package mybatis.mapper;

import mybatis.entity.DatalogUploadRecord;
import mybatis.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface DatalogUploadRecordMapper {
    int addDatalogUploadRecord(DatalogUploadRecord datalogUploadRecord);

}