package ar.edu.itba.paw.interfaces.builders;

import ar.edu.itba.paw.interfaces.WhereConditionBuilder;

public class HqlWhereConditionBuilder implements WhereConditionBuilder {

    private StringBuilder condition;

    public HqlWhereConditionBuilder() {
        this.condition = new StringBuilder();
    }

    @Override
    public WhereConditionBuilder begin() {
        condition = new StringBuilder();
        return this;
    }

    @Override
    public HqlWhereConditionBuilder equalityCondition(String left, String right) {
        comparisonCondition(left, " = ", right);
        return this;
    }

    @Override
    public HqlWhereConditionBuilder greaterThanCondition(String left, String right) {
        comparisonCondition(left, " > ", right);
        return this;
    }

    @Override
    public HqlWhereConditionBuilder lessThanCondition(String left, String right) {
        comparisonCondition(left, " < ", right);
        return this;
    }

    @Override
    public HqlWhereConditionBuilder greaterOrEqualThanCondition(String left, String right) {
        comparisonCondition(left, " >= ", right);
        return this;
    }

    @Override
    public HqlWhereConditionBuilder lessOrEqualThanCondition(String left, String right) {
        comparisonCondition(left, " <= ", right);
        return this;
    }

    @Override
    public HqlWhereConditionBuilder simpleInCondition(String item, String subgroup) {
        condition.append(item);
        condition.append(" in ( FROM ");
        condition.append(subgroup);
        condition.append(" )  AND ");
        return this;
    }

    @Override
    public WhereConditionBuilder descriptionCondition(String description, String caption, String searchQuery) {
        condition.append("(" + description );
        condition.append(" LIKE ");
        condition.append(searchQuery);
        condition.append(" OR ");
        condition.append(caption);
        condition.append(" LIKE ");
        condition.append(searchQuery);
        condition.append(")");
        condition.append(" AND ");
        return this;
    }

    @Override
    public String build() {
        deleteTrailingAnd();
        return condition.toString();
    }

    @Override
    public StringBuilder buildAsStringBuilder() {
        condition.length();
        deleteTrailingAnd();
        return condition;
    }

    private void deleteTrailingAnd() {
        if(conditionHasTrailingAnd())
            condition.delete(condition.length() - 5, condition.length());
    }

    private boolean conditionHasTrailingAnd() {
        return condition.length() != 0 && condition.substring(condition.length() - 5, condition.length()).equals(" AND ");
    }

    private void comparisonCondition(String left, String operator, String right) {
        condition.append(left);
        condition.append(operator);
        condition.append(right);
        condition.append("  AND ");
    }
}
