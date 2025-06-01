package dev.henriquebraga.tasks_backend.model.entity;

public enum TaskPriority {

  ALTA("Alta"),
  MEDIA("Média"),
  BAIXA("Baixa");

  private final String label;

  TaskPriority(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  public static TaskPriority fromLabel(String label) {
    for (TaskPriority p : values()) {
      if(p.label.equalsIgnoreCase(label)) {
        return p;
      }
    }
    throw new IllegalArgumentException("Prioridade inválida: " + label);
  }

}
