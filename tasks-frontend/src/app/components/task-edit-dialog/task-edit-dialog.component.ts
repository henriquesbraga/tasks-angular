import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import {
  Responsable,
  Task,
  TaskEdit,
  TaskService,
} from '../../services/task.service';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MAT_DATE_FORMATS } from '@angular/material/core';

export const MY_DATE_FORMATS = {
  parse: {
    dateInput: 'DD/MM/YYYY',
  },
  display: {
    dateInput: 'DD/MM/YYYY',
    monthYearLabel: 'MMM YYYY',
    dateA11yLabel: 'DD/MM/YYYY',
    monthYearA11yLabel: 'MMMM YYYY',
  },
};

@Component({
  selector: 'app-task-edit-dialog',
  imports: [
    CommonModule,
    FormsModule,
    MatDialogModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSnackBarModule,
  ],
  templateUrl: './task-edit-dialog.component.html',
  styleUrl: './task-edit-dialog.component.css',
  providers: [
    { provide: MAT_DATE_FORMATS, useValue: MY_DATE_FORMATS }
  ]
})
export class TaskEditDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<TaskEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Task,
    private taskService: TaskService,
    private snackBar: MatSnackBar
  ) {}

  responsaveis: Responsable[] = [];
  responsavelSelecionadoId: number | null = null;

  dataSelecionada: Date | null = null;
  dataFormatada: string = '';
  dataFormatadaView: string = '';

  ngOnInit() {
    this.taskService.getResponsables().subscribe({
      next: (data) => {
        this.responsaveis = data;

        console.log('this.data.personDTO.id', this.data.personDto.id);

        if (this.data.personDto.id) {
          this.responsavelSelecionadoId = this.data.personDto.id;
        }

        if (this.data.deadline) {
          this.formatarData(new Date(this.data.deadline));
        }
      },
      error: (err) => {
        console.error('Erro ao carregar responsaveis:', err);
      },
    });
  }

  formatarData(data: Date | null) {
    if (data) {
      const ano = data.getFullYear();
      const mes = String(data.getMonth() + 1).padStart(2, '0');
      const dia = String(data.getDate()).padStart(2, '0');
      this.dataFormatada = `${ano}-${mes}-${dia}`; // formato para envio (yyyy-MM-dd)
      this.dataFormatadaView = `${dia}/${mes}/${ano}`; // formato para exibição
      this.dataSelecionada = data;
    }
  }

  toDto(task: Task): TaskEdit {
    const dto: TaskEdit = {
      deadline: this.dataFormatada,
      description: task.description,
      personId: this.responsavelSelecionadoId!,
      priority: task.priority,
      status: task.status,
      title: task.title,
    };
    return dto;
  }

  salvar(task: Task) {
    if (
      !task.title?.trim() ||
      !task.description?.trim() ||
      !this.responsavelSelecionadoId ||
      !this.dataSelecionada
    ) {
      alert('Preencha todos os campos obrigatórios!');
      return;
    }
    this.taskService.updateTask(this.toDto(task), task.id).subscribe({
      next: (data) => {
        this.snackBar.open(data.status, 'Fechar', {
          duration: 3000,
          horizontalPosition: 'center',
          verticalPosition: 'bottom',
        });
      },
      error: (err) => {
        console.log('err', err)
        this.snackBar.open('Erro ao atualizar tarefa!', 'Fechar', {
          duration: 3000,
          horizontalPosition: 'center',
          verticalPosition: 'bottom',
        });
      },
    });
    this.dialogRef.close('update');
  }
}
