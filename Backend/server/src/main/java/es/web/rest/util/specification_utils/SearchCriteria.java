package es.web.rest.util.specification_utils;

public class SearchCriteria {

  private String key;
  private Object value;
  private SearchOperation operation;
  private LogicalOperation logicalOperation;

  public SearchCriteria(
      String key, Object value, SearchOperation operation, LogicalOperation logicalOperation) {
    this.key = key;
    this.value = value;
    this.operation = operation;
    this.logicalOperation = logicalOperation;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public SearchOperation getOperation() {
    return operation;
  }

  public void setOperation(SearchOperation operation) {
    this.operation = operation;
  }

  public LogicalOperation getLogicalOperation() {
    return logicalOperation;
  }

  public void setLogicalOperation(LogicalOperation logicalOperation) {
    this.logicalOperation = logicalOperation;
  }
}
