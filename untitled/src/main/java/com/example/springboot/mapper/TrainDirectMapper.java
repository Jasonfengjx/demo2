package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.domain.TrainTransfer;
import com.example.springboot.domain.TranDirect;
import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Mapper完成三个功能
 * 一个是完成直达的功能
 *
 * @Param startstation
 * @Param endstation
 * 一个是根据目的站和到达站查询途径站
 * 这个要两个no 一个是startstation 的no
 * 另一个是endstation的no
 * 另一个是根据起始站和终点站查询换乘的列车信息
 */

@Mapper
@Repository
public interface TrainDirectMapper {

    @Select(
            "select C.train_id as train_no ,C.train_number as train_number ,\n" +
                    " C.station_name as start_station ,D.station_name as end_station ,\n" +
                    " C.station_no as start_no , D.station_no as end_no  ,\n" +
                    " C.start_time as start_time , D.arrive_time as arrive_time,\n" +
                    " C.running_time as start_running_time ,D.running_time as end_running_time \n" +
                    " from train_parkingstation as C ,train_parkingstation as D \n" +
                    " where C.train_id = D.train_id \n" +
                    " and C.station_name = #{start_station} and D.station_name = #{end_station}\n" +
                    " and C.train_id in (select A.train_id from \n" +
                    "train_parkingstation as A ,train_parkingstation as B \n" +
                    "where  A.train_id = B.train_id and \n" +
                    "A.station_name = #{start_station} and\n" +
                    " B.station_name = #{end_station} \n" +
                    " and A.station_no <B.station_no)"
    )
    List<TranDirect> getDirectTrain(@Param("start_station") String start_station, @Param("end_station") String end_station);

    @Select("select A.train_id as train_no, A.train_number as train_number ,\n" +
            "A.station_name as start_station ,B.station_name as end_station , \n" +
            " A.station_no as start_no , B.station_no as  end_no  ,\n" +
            "  A.start_time as start_time , B.arrive_time as arrive_time,\n" +
            "   A.running_time as start_running_time ,B.running_time as end_running_time \n" +
            "    from train_parkingstation as A ,train_parkingstation as B \n" +
            "    where A.station_no between #{start_no} and #{end_no} \n" +
            "    and  B.station_no between #{start_no} and #{end_no} \n" +
            "    and A.train_id = #{train_no} \n" +
            "    and A.train_id = B.train_id \n" +
            "    and B.station_no = A.station_no +1 " +
            "    order by A.station_id ,B.station_id ")
    List<TranDirect> getViaStation(@Param("train_no") String train_no, @Param("start_no") String start_no, @Param("end_no") String end_no);

    @Select("select count(*) from" +
            "(select A.train_id as train_no, A.train_number as train_number ,\n" +
            "A.station_name as start_station ,B.station_name as end_station , \n" +
            " A.station_no as start_no , B.station_no as  end_no  ,\n" +
            "  A.start_time as start_time , B.arrive_time as arrive_time,\n" +
            "   A.running_time as start_running_time ,B.running_time as end_running_time \n" +
            "    from train_parkingstation as A ,train_parkingstation as B \n" +
            "    where A.station_no between #{start_no} and #{end_no} \n" +
            "    and  B.station_no between #{start_no} and #{end_no} \n" +
            "    and A.train_id = #{train_no} \n" +
            "    and A.train_id = B.train_id \n" +
            "    and B.station_no = A.station_no +1 " +
            "    order by A.station_id ,B.station_id )")
    Integer getViaStationNum(@Param("train_no") String train_no, @Param("start_no") String start_no, @Param("end_no") String end_no);

    @Select("select A.train_id as train_no_1 ,A.train_number as train_number_1, D.train_id as train_no_2 , " +
            "D.train_number as train_number_2, A.station_no as start_station_no,A.station_name as start_station_name," +
            "B.station_no as transfer_station_no_1 , B.station_name as transfer_station_name ,C.station_no as transfer_station_no_2,A.start_time as start_time_1," +
            "B.arrive_time as arrive_time_1 , C.start_time as start_time_2 ,D.arrive_time as arrive_time_2," +
            "D.station_no as end_station_no, D.station_name as end_station_name, A.running_time as start_running_time_1," +
            "B.running_time as end_running_time_1 ,C.running_time as start_running_time_2, D.running_time as end_running_time_2 " +
            "from  train_parkingstation as A , train_parkingstation as B , train_parkingstation as C ,train_parkingstation as D " +
            "where A.station_name = #{start_station} and D.station_name = #{end_station} and " +
            "A.train_id = B.train_id and B.station_name = C.station_name " +
            "and C.train_id = D.train_id and B.train_id <> C.train_id   and " +
            "B.arrive_time < C.arrive_time and A.train_id in (select X.train_id from " +
            "train_parkingstation as X ,train_parkingstation as Y " +
            "where  X.train_id = Y.train_id and" +
            " X.station_name = #{start_station} and Y.station_name = B.station_name and X.station_no < Y.station_no) " +
            " and C.train_id in (select X.train_id from train_parkingstation as X ,train_parkingstation as Y " +
            " where  X.train_id = Y.train_id and X.station_name = C.station_name and Y.station_name =  #{end_station} and X.station_no < Y.station_no) ")
    List<TrainTransfer> getTranserTrain(@Param("start_station") String start_station , @Param("end_station") String end_station);
    @Select("select * from User")
    List<User> getAllUsers();
}
