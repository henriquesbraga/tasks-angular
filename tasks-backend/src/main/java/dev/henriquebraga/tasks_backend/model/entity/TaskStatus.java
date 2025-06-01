package dev.henriquebraga.tasks_backend.model.entity;

public enum TaskStatus {

  EM_ANDAMENTO("Alta"),
  CONCLUIDA("Média");

  private final String label;

  TaskStatus(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  public static TaskStatus fromLabel(String label) {
    for (TaskStatus p : values()) {
      if(p.label.equalsIgnoreCase(label)) {
        return p;
      }
    }
    throw new IllegalArgumentException("Status inválido: " + label);
  }

}
