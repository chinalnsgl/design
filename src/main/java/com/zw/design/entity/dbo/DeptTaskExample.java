package com.zw.design.entity.dbo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DeptTaskExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DeptTaskExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andAnnotateIsNull() {
            addCriterion("annotate is null");
            return (Criteria) this;
        }

        public Criteria andAnnotateIsNotNull() {
            addCriterion("annotate is not null");
            return (Criteria) this;
        }

        public Criteria andAnnotateEqualTo(String value) {
            addCriterion("annotate =", value, "annotate");
            return (Criteria) this;
        }

        public Criteria andAnnotateNotEqualTo(String value) {
            addCriterion("annotate <>", value, "annotate");
            return (Criteria) this;
        }

        public Criteria andAnnotateGreaterThan(String value) {
            addCriterion("annotate >", value, "annotate");
            return (Criteria) this;
        }

        public Criteria andAnnotateGreaterThanOrEqualTo(String value) {
            addCriterion("annotate >=", value, "annotate");
            return (Criteria) this;
        }

        public Criteria andAnnotateLessThan(String value) {
            addCriterion("annotate <", value, "annotate");
            return (Criteria) this;
        }

        public Criteria andAnnotateLessThanOrEqualTo(String value) {
            addCriterion("annotate <=", value, "annotate");
            return (Criteria) this;
        }

        public Criteria andAnnotateLike(String value) {
            addCriterion("annotate like", value, "annotate");
            return (Criteria) this;
        }

        public Criteria andAnnotateNotLike(String value) {
            addCriterion("annotate not like", value, "annotate");
            return (Criteria) this;
        }

        public Criteria andAnnotateIn(List<String> values) {
            addCriterion("annotate in", values, "annotate");
            return (Criteria) this;
        }

        public Criteria andAnnotateNotIn(List<String> values) {
            addCriterion("annotate not in", values, "annotate");
            return (Criteria) this;
        }

        public Criteria andAnnotateBetween(String value1, String value2) {
            addCriterion("annotate between", value1, value2, "annotate");
            return (Criteria) this;
        }

        public Criteria andAnnotateNotBetween(String value1, String value2) {
            addCriterion("annotate not between", value1, value2, "annotate");
            return (Criteria) this;
        }

        public Criteria andCommentIsNull() {
            addCriterion("comment is null");
            return (Criteria) this;
        }

        public Criteria andCommentIsNotNull() {
            addCriterion("comment is not null");
            return (Criteria) this;
        }

        public Criteria andCommentEqualTo(String value) {
            addCriterion("comment =", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotEqualTo(String value) {
            addCriterion("comment <>", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentGreaterThan(String value) {
            addCriterion("comment >", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentGreaterThanOrEqualTo(String value) {
            addCriterion("comment >=", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentLessThan(String value) {
            addCriterion("comment <", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentLessThanOrEqualTo(String value) {
            addCriterion("comment <=", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentLike(String value) {
            addCriterion("comment like", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotLike(String value) {
            addCriterion("comment not like", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentIn(List<String> values) {
            addCriterion("comment in", values, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotIn(List<String> values) {
            addCriterion("comment not in", values, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentBetween(String value1, String value2) {
            addCriterion("comment between", value1, value2, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotBetween(String value1, String value2) {
            addCriterion("comment not between", value1, value2, "comment");
            return (Criteria) this;
        }

        public Criteria andDepartmentNumIsNull() {
            addCriterion("department_num is null");
            return (Criteria) this;
        }

        public Criteria andDepartmentNumIsNotNull() {
            addCriterion("department_num is not null");
            return (Criteria) this;
        }

        public Criteria andDepartmentNumEqualTo(Integer value) {
            addCriterion("department_num =", value, "departmentNum");
            return (Criteria) this;
        }

        public Criteria andDepartmentNumNotEqualTo(Integer value) {
            addCriterion("department_num <>", value, "departmentNum");
            return (Criteria) this;
        }

        public Criteria andDepartmentNumGreaterThan(Integer value) {
            addCriterion("department_num >", value, "departmentNum");
            return (Criteria) this;
        }

        public Criteria andDepartmentNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("department_num >=", value, "departmentNum");
            return (Criteria) this;
        }

        public Criteria andDepartmentNumLessThan(Integer value) {
            addCriterion("department_num <", value, "departmentNum");
            return (Criteria) this;
        }

        public Criteria andDepartmentNumLessThanOrEqualTo(Integer value) {
            addCriterion("department_num <=", value, "departmentNum");
            return (Criteria) this;
        }

        public Criteria andDepartmentNumIn(List<Integer> values) {
            addCriterion("department_num in", values, "departmentNum");
            return (Criteria) this;
        }

        public Criteria andDepartmentNumNotIn(List<Integer> values) {
            addCriterion("department_num not in", values, "departmentNum");
            return (Criteria) this;
        }

        public Criteria andDepartmentNumBetween(Integer value1, Integer value2) {
            addCriterion("department_num between", value1, value2, "departmentNum");
            return (Criteria) this;
        }

        public Criteria andDepartmentNumNotBetween(Integer value1, Integer value2) {
            addCriterion("department_num not between", value1, value2, "departmentNum");
            return (Criteria) this;
        }

        public Criteria andDepartmentTypeIsNull() {
            addCriterion("department_type is null");
            return (Criteria) this;
        }

        public Criteria andDepartmentTypeIsNotNull() {
            addCriterion("department_type is not null");
            return (Criteria) this;
        }

        public Criteria andDepartmentTypeEqualTo(Integer value) {
            addCriterion("department_type =", value, "departmentType");
            return (Criteria) this;
        }

        public Criteria andDepartmentTypeNotEqualTo(Integer value) {
            addCriterion("department_type <>", value, "departmentType");
            return (Criteria) this;
        }

        public Criteria andDepartmentTypeGreaterThan(Integer value) {
            addCriterion("department_type >", value, "departmentType");
            return (Criteria) this;
        }

        public Criteria andDepartmentTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("department_type >=", value, "departmentType");
            return (Criteria) this;
        }

        public Criteria andDepartmentTypeLessThan(Integer value) {
            addCriterion("department_type <", value, "departmentType");
            return (Criteria) this;
        }

        public Criteria andDepartmentTypeLessThanOrEqualTo(Integer value) {
            addCriterion("department_type <=", value, "departmentType");
            return (Criteria) this;
        }

        public Criteria andDepartmentTypeIn(List<Integer> values) {
            addCriterion("department_type in", values, "departmentType");
            return (Criteria) this;
        }

        public Criteria andDepartmentTypeNotIn(List<Integer> values) {
            addCriterion("department_type not in", values, "departmentType");
            return (Criteria) this;
        }

        public Criteria andDepartmentTypeBetween(Integer value1, Integer value2) {
            addCriterion("department_type between", value1, value2, "departmentType");
            return (Criteria) this;
        }

        public Criteria andDepartmentTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("department_type not between", value1, value2, "departmentType");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(Date value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(Date value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(Date value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(Date value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<Date> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<Date> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(Date value1, Date value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andPlanStartTimeIsNull() {
            addCriterion("plan_start_time is null");
            return (Criteria) this;
        }

        public Criteria andPlanStartTimeIsNotNull() {
            addCriterion("plan_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andPlanStartTimeEqualTo(Date value) {
            addCriterion("plan_start_time =", value, "planStartTime");
            return (Criteria) this;
        }

        public Criteria andPlanStartTimeNotEqualTo(Date value) {
            addCriterion("plan_start_time <>", value, "planStartTime");
            return (Criteria) this;
        }

        public Criteria andPlanStartTimeGreaterThan(Date value) {
            addCriterion("plan_start_time >", value, "planStartTime");
            return (Criteria) this;
        }

        public Criteria andPlanStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("plan_start_time >=", value, "planStartTime");
            return (Criteria) this;
        }

        public Criteria andPlanStartTimeLessThan(Date value) {
            addCriterion("plan_start_time <", value, "planStartTime");
            return (Criteria) this;
        }

        public Criteria andPlanStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("plan_start_time <=", value, "planStartTime");
            return (Criteria) this;
        }

        public Criteria andPlanStartTimeIn(List<Date> values) {
            addCriterion("plan_start_time in", values, "planStartTime");
            return (Criteria) this;
        }

        public Criteria andPlanStartTimeNotIn(List<Date> values) {
            addCriterion("plan_start_time not in", values, "planStartTime");
            return (Criteria) this;
        }

        public Criteria andPlanStartTimeBetween(Date value1, Date value2) {
            addCriterion("plan_start_time between", value1, value2, "planStartTime");
            return (Criteria) this;
        }

        public Criteria andPlanStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("plan_start_time not between", value1, value2, "planStartTime");
            return (Criteria) this;
        }

        public Criteria andPrincipalIsNull() {
            addCriterion("principal is null");
            return (Criteria) this;
        }

        public Criteria andPrincipalIsNotNull() {
            addCriterion("principal is not null");
            return (Criteria) this;
        }

        public Criteria andPrincipalEqualTo(String value) {
            addCriterion("principal =", value, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalNotEqualTo(String value) {
            addCriterion("principal <>", value, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalGreaterThan(String value) {
            addCriterion("principal >", value, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalGreaterThanOrEqualTo(String value) {
            addCriterion("principal >=", value, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalLessThan(String value) {
            addCriterion("principal <", value, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalLessThanOrEqualTo(String value) {
            addCriterion("principal <=", value, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalLike(String value) {
            addCriterion("principal like", value, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalNotLike(String value) {
            addCriterion("principal not like", value, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalIn(List<String> values) {
            addCriterion("principal in", values, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalNotIn(List<String> values) {
            addCriterion("principal not in", values, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalBetween(String value1, String value2) {
            addCriterion("principal between", value1, value2, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalNotBetween(String value1, String value2) {
            addCriterion("principal not between", value1, value2, "principal");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNull() {
            addCriterion("start_time is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("start_time is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(Date value) {
            addCriterion("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(Date value) {
            addCriterion("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(Date value) {
            addCriterion("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(Date value) {
            addCriterion("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<Date> values) {
            addCriterion("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<Date> values) {
            addCriterion("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(Date value1, Date value2) {
            addCriterion("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("start_time not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStepNameIsNull() {
            addCriterion("step_name is null");
            return (Criteria) this;
        }

        public Criteria andStepNameIsNotNull() {
            addCriterion("step_name is not null");
            return (Criteria) this;
        }

        public Criteria andStepNameEqualTo(String value) {
            addCriterion("step_name =", value, "stepName");
            return (Criteria) this;
        }

        public Criteria andStepNameNotEqualTo(String value) {
            addCriterion("step_name <>", value, "stepName");
            return (Criteria) this;
        }

        public Criteria andStepNameGreaterThan(String value) {
            addCriterion("step_name >", value, "stepName");
            return (Criteria) this;
        }

        public Criteria andStepNameGreaterThanOrEqualTo(String value) {
            addCriterion("step_name >=", value, "stepName");
            return (Criteria) this;
        }

        public Criteria andStepNameLessThan(String value) {
            addCriterion("step_name <", value, "stepName");
            return (Criteria) this;
        }

        public Criteria andStepNameLessThanOrEqualTo(String value) {
            addCriterion("step_name <=", value, "stepName");
            return (Criteria) this;
        }

        public Criteria andStepNameLike(String value) {
            addCriterion("step_name like", value, "stepName");
            return (Criteria) this;
        }

        public Criteria andStepNameNotLike(String value) {
            addCriterion("step_name not like", value, "stepName");
            return (Criteria) this;
        }

        public Criteria andStepNameIn(List<String> values) {
            addCriterion("step_name in", values, "stepName");
            return (Criteria) this;
        }

        public Criteria andStepNameNotIn(List<String> values) {
            addCriterion("step_name not in", values, "stepName");
            return (Criteria) this;
        }

        public Criteria andStepNameBetween(String value1, String value2) {
            addCriterion("step_name between", value1, value2, "stepName");
            return (Criteria) this;
        }

        public Criteria andStepNameNotBetween(String value1, String value2) {
            addCriterion("step_name not between", value1, value2, "stepName");
            return (Criteria) this;
        }

        public Criteria andStepNumIsNull() {
            addCriterion("step_num is null");
            return (Criteria) this;
        }

        public Criteria andStepNumIsNotNull() {
            addCriterion("step_num is not null");
            return (Criteria) this;
        }

        public Criteria andStepNumEqualTo(Integer value) {
            addCriterion("step_num =", value, "stepNum");
            return (Criteria) this;
        }

        public Criteria andStepNumNotEqualTo(Integer value) {
            addCriterion("step_num <>", value, "stepNum");
            return (Criteria) this;
        }

        public Criteria andStepNumGreaterThan(Integer value) {
            addCriterion("step_num >", value, "stepNum");
            return (Criteria) this;
        }

        public Criteria andStepNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("step_num >=", value, "stepNum");
            return (Criteria) this;
        }

        public Criteria andStepNumLessThan(Integer value) {
            addCriterion("step_num <", value, "stepNum");
            return (Criteria) this;
        }

        public Criteria andStepNumLessThanOrEqualTo(Integer value) {
            addCriterion("step_num <=", value, "stepNum");
            return (Criteria) this;
        }

        public Criteria andStepNumIn(List<Integer> values) {
            addCriterion("step_num in", values, "stepNum");
            return (Criteria) this;
        }

        public Criteria andStepNumNotIn(List<Integer> values) {
            addCriterion("step_num not in", values, "stepNum");
            return (Criteria) this;
        }

        public Criteria andStepNumBetween(Integer value1, Integer value2) {
            addCriterion("step_num between", value1, value2, "stepNum");
            return (Criteria) this;
        }

        public Criteria andStepNumNotBetween(Integer value1, Integer value2) {
            addCriterion("step_num not between", value1, value2, "stepNum");
            return (Criteria) this;
        }

        public Criteria andProjectIdIsNull() {
            addCriterion("project_id is null");
            return (Criteria) this;
        }

        public Criteria andProjectIdIsNotNull() {
            addCriterion("project_id is not null");
            return (Criteria) this;
        }

        public Criteria andProjectIdEqualTo(Integer value) {
            addCriterion("project_id =", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotEqualTo(Integer value) {
            addCriterion("project_id <>", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdGreaterThan(Integer value) {
            addCriterion("project_id >", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("project_id >=", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLessThan(Integer value) {
            addCriterion("project_id <", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLessThanOrEqualTo(Integer value) {
            addCriterion("project_id <=", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdIn(List<Integer> values) {
            addCriterion("project_id in", values, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotIn(List<Integer> values) {
            addCriterion("project_id not in", values, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdBetween(Integer value1, Integer value2) {
            addCriterion("project_id between", value1, value2, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotBetween(Integer value1, Integer value2) {
            addCriterion("project_id not between", value1, value2, "projectId");
            return (Criteria) this;
        }

        public Criteria andStepNoIsNull() {
            addCriterion("step_no is null");
            return (Criteria) this;
        }

        public Criteria andStepNoIsNotNull() {
            addCriterion("step_no is not null");
            return (Criteria) this;
        }

        public Criteria andStepNoEqualTo(Integer value) {
            addCriterion("step_no =", value, "stepNo");
            return (Criteria) this;
        }

        public Criteria andStepNoNotEqualTo(Integer value) {
            addCriterion("step_no <>", value, "stepNo");
            return (Criteria) this;
        }

        public Criteria andStepNoGreaterThan(Integer value) {
            addCriterion("step_no >", value, "stepNo");
            return (Criteria) this;
        }

        public Criteria andStepNoGreaterThanOrEqualTo(Integer value) {
            addCriterion("step_no >=", value, "stepNo");
            return (Criteria) this;
        }

        public Criteria andStepNoLessThan(Integer value) {
            addCriterion("step_no <", value, "stepNo");
            return (Criteria) this;
        }

        public Criteria andStepNoLessThanOrEqualTo(Integer value) {
            addCriterion("step_no <=", value, "stepNo");
            return (Criteria) this;
        }

        public Criteria andStepNoIn(List<Integer> values) {
            addCriterion("step_no in", values, "stepNo");
            return (Criteria) this;
        }

        public Criteria andStepNoNotIn(List<Integer> values) {
            addCriterion("step_no not in", values, "stepNo");
            return (Criteria) this;
        }

        public Criteria andStepNoBetween(Integer value1, Integer value2) {
            addCriterion("step_no between", value1, value2, "stepNo");
            return (Criteria) this;
        }

        public Criteria andStepNoNotBetween(Integer value1, Integer value2) {
            addCriterion("step_no not between", value1, value2, "stepNo");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}