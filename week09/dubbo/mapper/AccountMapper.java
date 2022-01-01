package com.yunmu.geek.mapper;

import com.yunmu.geek.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AccountMapper {

    @Update("update `himly_dubbo_account` set us_wallet = us_wallet + #{usWallet}, cny_wallet = cny_wallet + " +
            "#{cnyWallet} where us_wallet >= #{usWallet} and cny_wallet >= #{cnyWallet} and id = #{id}")
    boolean payment(Account account);

    @Select("select * from himly_dubbo_account where id = #{id}")
    Account queryOne(Account account);
}
