package ar.edu.itba.paw.interfaces;

public interface WhereConditionBuilder {
    WhereConditionBuilder begin();
    WhereConditionBuilder equalityCondition(String left, String right);
    WhereConditionBuilder greaterThanCondition(String left, String right);
    WhereConditionBuilder lessThanCondition(String left, String right);
    WhereConditionBuilder simpleInCondition(String item, String subquery);
    WhereConditionBuilder descriptionCondition(String description, String caption, String searchQuery);
    String build();
    StringBuilder buildAsStringBuilder();
}
