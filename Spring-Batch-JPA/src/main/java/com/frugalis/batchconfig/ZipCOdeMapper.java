package com.frugalis.batchconfig;

import com.frugalis.entity.ZIPCodes;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class ZipCOdeMapper implements FieldSetMapper<ZIPCodes> {

    @Override
    public ZIPCodes mapFieldSet(FieldSet fieldSet) throws BindException {
        return new ZIPCodes(
                fieldSet.readString("Zip_Code"),
                fieldSet.readString("Official_USPS_city_name"),
                fieldSet.readString("Official_USPS_State_Code"));
    }
}