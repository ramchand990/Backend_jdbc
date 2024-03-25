package com.healspan.claim.constant;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AdminQueries {

    public static final String GENERIC_INSERT_QUERY =
            "insert into healspan.{0} (name) values(?)";

    public static final String HOSPITAL_MST_INSERT_QUERY =
            "insert into healspan.{0} (hospital_id,name) values(?,?)";

    public static final String USER_MST_INSERT_QUERY =
            "insert into healspan.{0} (" +
                    "username,email,first_name,is_active,mobile_no,last_name,password," +
                    "hospital_mst_id,user_role_mst_id) " +
                    "values(?,?,?,?,?,?,?,?,?)";

    public static final String GENERIC_UPDATE_QUERY =
            "update healspan.{0} set name ='{1}' where id = {2}";

    public static final String HOSPITAL_MST_UPDATE_QUERY =
            "update healspan.{0} set hospital_id = '{1}' , name= '{2}' where id = {3}";

    public static final String USER_MST_UPDATE_QUERY =
            "update healspan.{0} set username = {1}, first_name = {2}," +
                    "email = {3}, is_active = {4}, mobile_no = {5}, last_name = {6}, password = {7}," +
                    "hospital_mst_id = {8}, user_role_mst_id={9} where id = {10}";

    public static final String TPA_WISE_MASTER_DETAILS =
            "select * from healspan.get_tpawise_master_details({0})";

    public static final String GET_MASTER_DETAILS =
            "select * from healspan.get_master_details({0})";

    private AdminQueries() {
    }
}
