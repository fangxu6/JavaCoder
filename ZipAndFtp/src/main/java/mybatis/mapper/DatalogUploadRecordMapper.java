package mybatis.mapper;

import mybatis.entity.DatalogUploadRecord;
import mybatis.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface DatalogUploadRecordMapper {

    DatalogUploadRecord selectByZipFileName(String zipFileName);

    int addDatalogUploadRecord(DatalogUploadRecord datalogUploadRecord);

    int updateByPrimaryKey(DatalogUploadRecord datalogUploadRecord);


}