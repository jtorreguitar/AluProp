package ar.edu.itba.paw.interfaces;

public interface WhereConditionBuilder {
    WhereConditionBuilder begin();
    WhereConditionBuilder equalityCondition(String left, String right);
    WhereConditionBuilder greaterThanCondition(String left, String right);
    WhereConditionBuilder lessThanCondition(String left, String right);
    WhereConditionBuilder simpleInCondition(String item, String subquery);
    WhereConditionBuilder likeCondition(String left, String right);
    String build();
    StringBuilder buildAsStringBuilder();
}
