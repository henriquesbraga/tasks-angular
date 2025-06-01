import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import {
  Responsable,
  Task,
  TaskEdit,
  TaskNew,
  TaskService,
} from '../../services/task.service';
import { FormsModule, NgForm } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MAT_DATE_FORMATS, MatNativeDateModule } from '@angular/material/core';
import { MY_DATE_FORMATS } from '../../utils/my_date_formats'; 

import dayjs from 'dayjs';

@Component({
  selector: 'app-task-new-dialog',
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
  templateUrl: './task-new-dialog.component.html',
  styleUrl: './task-new-dialog.component.css',
  providers: [{ provide: MAT_DATE_FORMATS, useValue: MY_DATE_FORMATS }],
})
export class TaskNewDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<TaskNewDialogComponent>,
    private taskService: TaskService,
    private snackBar: MatSnackBar
  ) {}

  responsaveis: Responsable[] = [];
  responsavelSelecionadoId: number | null = null;
  dataSelecionada: Date | null = null;
  dataFormatada: string = '';

  prioridades: { id: string; name: string }[] = [
    { id: 'BAIXA', name: 'Baixa' },
    { id: 'MEDIA', name: 'Média' },
    { id: 'ALTA', name: 'Alta' },
  ];

  data: TaskNew = {
    deadline: '',
    description: '',
    personId: 0,
    priority: '',
    title: '',
  };

  ngOnInit() {
    this.taskService.getResponsables().subscribe({
      next: (data) => {
        this.responsaveis = data;
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

  salvar(form: NgForm) {
    const deadline = dayjs(form.value.data).format('YYYY-MM-DD').toString();
    const dataToSent: TaskNew = {
      ...form.value,
      deadline,
    };
    this.taskService.newTask(dataToSent).subscribe({
      next: (data) => {
        this.dialogRef.close('update');
        this.snackBar.open(data.status, 'Fechar', {
          duration: 3000,
          horizontalPosition: 'center',
          verticalPosition: 'bottom',
        });
      },
      error: (data) => {
        console.log('err', data);
        this.snackBar.open('Erro ao adicionar tarefa!', 'Fechar', {
          duration: 3000,
          horizontalPosition: 'center',
          verticalPosition: 'bottom',
        });
      },
    });

    //this.dialogRef.close();
  }
}
