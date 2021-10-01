package com.example.springboot.mapper;

import com.example.springboot.entity.Seat;
import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SeatMapper extends MppBaseMapper<Seat> {
    @Select("select train_id,seat_type,sum(seat_sum) as seat_sum,basic_cost " +
            "from seat " +
            "where train_id=#{trainId} "+
            "group by seat_type,train_id,basic_cost "
            )
    public List<Seat> findSeats(@Param("trainId") String trainId);

    @Update("update seat " +
            "set seat_sum-=num " +
            "where train_id=#{train_id} and room_id=#{room_id} and")
    public Integer updateSeat(@Param("num") Integer num,
                              @Param("train_id") String train_id,
                              @Param("room_id") String room_id,
                              @Param("seat_type") String seat_type);
    @Select("select seat_sum" +
            "from seat" +
            "where train_id=#{train_id} and room_id=#{room_id} and seat_type=#{seat_type}")
    public Integer selectTypeNum(@Param("train_id") String train_id,
                                 @Param("room_id") String room_id,
                                 @Param("seat_type") String seat_type);
}
