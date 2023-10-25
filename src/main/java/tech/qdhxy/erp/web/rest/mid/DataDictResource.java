package tech.qdhxy.erp.web.rest.mid;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.qdhxy.erp.common.exceptions.BadRequestException;
import tech.qdhxy.erp.common.utils.Constants;
import tech.qdhxy.erp.common.vo.MyPage;
import tech.qdhxy.erp.domain.mid.DataDict;
import tech.qdhxy.erp.service.mid.DataDictService;
import tech.qdhxy.erp.service.mid.dto.DataDictDTO;
import tech.qdhxy.erp.web.rest.mid.query.DataDictQuery;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/api/mid/data-dicts")
public class DataDictResource {
    private final DataDictService dataDictService;

    @GetMapping()
    public MyPage<DataDictDTO> getPage(DataDictQuery query) {
        return MyPage.of(this.dataDictService.getPage(query));
    }

    /**
     * 根据group key查询数据字典列表
     *
     * @param groupKey
     * @return
     */
    @GetMapping("/group-key")
    public List<DataDictDTO> getDictByGroupKey(@RequestParam String groupKey) {
        return this.dataDictService.getDictByGroupKey(groupKey);
    }
    @GetMapping("/meta")
    public List<DataDictDTO> getDictByGroupKey() {
        return this.dataDictService.getDictByGroupKey(Constants.DATA_DICT_META_GROUP);
    }


    @PostMapping()
    public DataDictDTO create(@Valid @RequestBody DataDictDTO dataDictDTO) {
        return dataDictService.save(dataDictDTO);
    }

    @PutMapping()
    public DataDictDTO update(@Valid @RequestBody DataDictDTO dataDictDTO) {
        if(Objects.isNull(dataDictDTO.getId())){
            throw new BadRequestException("ID不能为空");
        }
        return dataDictService.save(dataDictDTO);
    }

    @PutMapping("/{id}/status/{status}")
    public void updateStatus(@PathVariable Long id,
                                    @PathVariable Boolean status) {
        dataDictService.updateStatus(id, status);
    }

}
