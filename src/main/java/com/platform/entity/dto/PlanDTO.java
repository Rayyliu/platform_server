package com.platform.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PlanDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column plan.id
     *
     * @mbg.generated Sun Oct 18 11:24:58 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column plan.plan_name
     *
     * @mbg.generated Sun Oct 18 11:24:58 CST 2020
     */
    private String planName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column plan.plan_description
     *
     * @mbg.generated Sun Oct 18 11:24:58 CST 2020
     */
    private String planDescription;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column plan.project
     *
     * @mbg.generated Sun Oct 18 11:24:58 CST 2020
     */
    private String project;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column plan.env
     *
     * @mbg.generated Sun Oct 18 11:24:58 CST 2020
     */
    private String env;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column plan.plan_content
     *
     * @mbg.generated Sun Oct 18 11:24:58 CST 2020
     */
    private String planContent;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column plan.plan_execute_result
     *
     * @mbg.generated Sun Oct 18 11:24:58 CST 2020
     */
    private String planExecuteResult;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column plan.tester
     *
     * @mbg.generated Sun Oct 18 11:24:58 CST 2020
     */
    private String tester;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column plan.execute_at
     *
     * @mbg.generated Sun Oct 18 11:24:58 CST 2020
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date executeAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column plan.update_at
     *
     * @mbg.generated Sun Oct 18 11:24:58 CST 2020
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column plan.create_at
     *
     * @mbg.generated Sun Oct 18 11:24:58 CST 2020
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;

}
