package tech.qdhxy.erp.service.mid;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.qdhxy.erp.common.utils.Constants;
import tech.qdhxy.erp.domain.mid.DataDict;
import tech.qdhxy.erp.repository.mid.DataDictRepository;
import tech.qdhxy.erp.service.mid.dto.DataDictDTO;
import tech.qdhxy.erp.service.mid.mapper.DataDictMapper;
import tech.qdhxy.erp.web.rest.mid.query.DataDictQuery;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DataDictService extends ServiceImpl<DataDictRepository, DataDict> {
    private final DataDictMapper dataDictMapper;

    public List<DataDictDTO> getDictByGroupKey(String groupKey) {
        DataDict meta = this.baseMapper.selectOne(Wrappers.<DataDict>lambdaQuery()
                .eq(DataDict::getGroupKey, Constants.DATA_DICT_META_GROUP)
                .eq(DataDict::getItemKey, groupKey));
        return this.baseMapper.selectList(Wrappers.<DataDict>lambdaQuery()
                        .eq(DataDict::getGroupKey, groupKey)
                        .eq(DataDict::getStatus, true))
                .stream()
                .map(e -> {
                    DataDictDTO dto = dataDictMapper.toDto(e);
                    dto.setGroupName(Optional.ofNullable(meta).map(DataDict::getItemValue).orElse(""));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public DataDictDTO save(DataDictDTO dataDictDTO) {
        DataDict dataDict = dataDictMapper.toEntity(dataDictDTO);
        if (Objects.nonNull(dataDictDTO.getId())) {
            this.baseMapper.updateById(dataDict);
        } else {
            this.baseMapper.insert(dataDict);
        }
        insertOrUpdateMeta(dataDictDTO);
        return dataDictMapper.toDto(dataDict);
    }

    public void insertOrUpdateMeta(DataDictDTO dataDictDTO) {
        boolean exist = this.baseMapper.exists(Wrappers.<DataDict>lambdaQuery()
                .eq(DataDict::getGroupKey, Constants.DATA_DICT_META_GROUP)
                .eq(DataDict::getItemKey, dataDictDTO.getGroupKey()));
        if (!exist) {
            DataDict dataDict = new DataDict();
            dataDict.setStatus(true);
            dataDict.setGroupKey(Constants.DATA_DICT_META_GROUP);
            dataDict.setItemKey(dataDictDTO.getGroupKey());
            dataDict.setItemValue(dataDictDTO.getGroupName());
            this.baseMapper.insert(dataDict);
        } else {
            this.baseMapper.update(null, Wrappers.<DataDict>lambdaUpdate()
                    .set(DataDict::getItemValue, dataDictDTO.getGroupName())
                    .eq(DataDict::getGroupKey, Constants.DATA_DICT_META_GROUP)
                    .eq(DataDict::getItemKey, dataDictDTO.getGroupKey()));
        }
    }

    public IPage<DataDictDTO> getPage(DataDictQuery query) {
        IPage<DataDict> pageList = this.baseMapper.selectPage(query.getPage(), query.getWrapper());
        Set<String> groupKeys = pageList.getRecords().stream().map(DataDict::getGroupKey).collect(Collectors.toSet());
        Map<String, String> groupNameMap = Optional.of(groupKeys)
                .filter(x -> !x.isEmpty())
                .map(keys -> this.baseMapper.selectList(Wrappers.<DataDict>lambdaQuery()
                                .eq(DataDict::getGroupKey, Constants.DATA_DICT_META_GROUP)
                                .in(DataDict::getItemKey, keys))
                        .stream()
                        .collect(Collectors.toMap(DataDict::getItemKey, DataDict::getItemValue)))
                .orElseGet(HashMap::new);
        return pageList.convert(e -> {
            DataDictDTO dto = dataDictMapper.toDto(e);
            dto.setGroupName(groupNameMap.getOrDefault(dto.getGroupKey(), ""));
            return dto;
        });
    }

    public void updateStatus(Long id, Boolean status) {
        this.baseMapper.update(null, Wrappers.<DataDict>lambdaUpdate()
                .set(DataDict::getStatus, status)
                .eq(DataDict::getId, id));
    }
}
