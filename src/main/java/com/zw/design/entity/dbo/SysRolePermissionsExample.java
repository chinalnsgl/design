package com.zw.design.entity.dbo;

import java.util.ArrayList;
import java.util.List;

public class SysRolePermissionsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SysRolePermissionsExample() {
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

        public Criteria andSysRoleIdIsNull() {
            addCriterion("sys_role_id is null");
            return (Criteria) this;
        }

        public Criteria andSysRoleIdIsNotNull() {
            addCriterion("sys_role_id is not null");
            return (Criteria) this;
        }

        public Criteria andSysRoleIdEqualTo(Integer value) {
            addCriterion("sys_role_id =", value, "sysRoleId");
            return (Criteria) this;
        }

        public Criteria andSysRoleIdNotEqualTo(Integer value) {
            addCriterion("sys_role_id <>", value, "sysRoleId");
            return (Criteria) this;
        }

        public Criteria andSysRoleIdGreaterThan(Integer value) {
            addCriterion("sys_role_id >", value, "sysRoleId");
            return (Criteria) this;
        }

        public Criteria andSysRoleIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("sys_role_id >=", value, "sysRoleId");
            return (Criteria) this;
        }

        public Criteria andSysRoleIdLessThan(Integer value) {
            addCriterion("sys_role_id <", value, "sysRoleId");
            return (Criteria) this;
        }

        public Criteria andSysRoleIdLessThanOrEqualTo(Integer value) {
            addCriterion("sys_role_id <=", value, "sysRoleId");
            return (Criteria) this;
        }

        public Criteria andSysRoleIdIn(List<Integer> values) {
            addCriterion("sys_role_id in", values, "sysRoleId");
            return (Criteria) this;
        }

        public Criteria andSysRoleIdNotIn(List<Integer> values) {
            addCriterion("sys_role_id not in", values, "sysRoleId");
            return (Criteria) this;
        }

        public Criteria andSysRoleIdBetween(Integer value1, Integer value2) {
            addCriterion("sys_role_id between", value1, value2, "sysRoleId");
            return (Criteria) this;
        }

        public Criteria andSysRoleIdNotBetween(Integer value1, Integer value2) {
            addCriterion("sys_role_id not between", value1, value2, "sysRoleId");
            return (Criteria) this;
        }

        public Criteria andPermissionsIdIsNull() {
            addCriterion("permissions_id is null");
            return (Criteria) this;
        }

        public Criteria andPermissionsIdIsNotNull() {
            addCriterion("permissions_id is not null");
            return (Criteria) this;
        }

        public Criteria andPermissionsIdEqualTo(Integer value) {
            addCriterion("permissions_id =", value, "permissionsId");
            return (Criteria) this;
        }

        public Criteria andPermissionsIdNotEqualTo(Integer value) {
            addCriterion("permissions_id <>", value, "permissionsId");
            return (Criteria) this;
        }

        public Criteria andPermissionsIdGreaterThan(Integer value) {
            addCriterion("permissions_id >", value, "permissionsId");
            return (Criteria) this;
        }

        public Criteria andPermissionsIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("permissions_id >=", value, "permissionsId");
            return (Criteria) this;
        }

        public Criteria andPermissionsIdLessThan(Integer value) {
            addCriterion("permissions_id <", value, "permissionsId");
            return (Criteria) this;
        }

        public Criteria andPermissionsIdLessThanOrEqualTo(Integer value) {
            addCriterion("permissions_id <=", value, "permissionsId");
            return (Criteria) this;
        }

        public Criteria andPermissionsIdIn(List<Integer> values) {
            addCriterion("permissions_id in", values, "permissionsId");
            return (Criteria) this;
        }

        public Criteria andPermissionsIdNotIn(List<Integer> values) {
            addCriterion("permissions_id not in", values, "permissionsId");
            return (Criteria) this;
        }

        public Criteria andPermissionsIdBetween(Integer value1, Integer value2) {
            addCriterion("permissions_id between", value1, value2, "permissionsId");
            return (Criteria) this;
        }

        public Criteria andPermissionsIdNotBetween(Integer value1, Integer value2) {
            addCriterion("permissions_id not between", value1, value2, "permissionsId");
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