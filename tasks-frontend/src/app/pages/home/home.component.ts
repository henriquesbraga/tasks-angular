import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTableModule } from '@angular/material/table';
import {
  Responsable,
  Task,
  TaskEdit,
  TaskRequestFilters,
  TaskService,
} from '../../services/task.service';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { AuthService } from '../../services/auth.service';
import { Route, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatDialog } from '@angular/material/dialog';
import { TaskEditDialogComponent } from '../../components/task-edit-dialog/task-edit-dialog.component';
import { TaskNewDialogComponent } from '../../components/task-new-dialog/task-new-dialog.component';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

@Component({
  selector: 'app-home',
  imports: [
    CommonModule,
    MatTableModule,
    MatCardModule,
    MatProgressSpinnerModule,
    MatSidenavModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    FormsModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    MatExpansionModule,
    MatSnackBarModule,
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent implements OnInit {
  tasks: Task[] = [];
  loading = true;
  filtro: TaskRequestFilters = {
    numero: null,
    titulo: '',
    responsavel: '',
    situacao: 'EM_ANDAMENTO',
  };

  responsaveis: Responsable[] = [{ id: 0, name: 'Todos' }]; // simulação
  situacoes: { value: String; name: String }[] = [
    { value: 'EM_ANDAMENTO', name: 'Em andamento' },
    { value: 'CONCLUIDA', name: 'Concluída' },
  ];

  displayedColumns = [
    'numero',
    'title',
    //'description',
    'responsable',
    'actions',
    //'deadline',
    //'priority',
    //'status',
  ];

  constructor(
    private taskService: TaskService,
    private authService: AuthService,
    private router: Router,
    private dialog: MatDialog,
    private newTaskdialog: MatDialog,
    private snackBar: MatSnackBar
  ) {}

  username = sessionStorage.getItem('userName');

  ngOnInit() {
    if (
      !sessionStorage.getItem('token') ||
      sessionStorage.getItem('token') == ''
    ) {
      this.logout();
    }

    this.buscarTarefas();

    this.taskService.getResponsables().subscribe({
      next: (data) => {
        this.responsaveis = [...this.responsaveis, ...data];
      },
      error: (err) => {
        console.error('Erro ao carregar responsaveis:', err);
      },
    });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['login']);
  }

  buscarTarefas() {
    this.loading = true;
    this.taskService.getTasks(this.filtro).subscribe({
      next: (data) => {
        this.tasks = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Erro ao carregar tarefas:', err);
        this.loading = false;
      },
    });
  }

  editarTask(task: Task) {
    const editRef =  this.dialog.open(TaskEditDialogComponent, {
      width: '400px',
      data: task,
    });

    editRef.afterClosed().subscribe(res => {
      if(res === 'update') {
        this.buscarTarefas()
      }
    })
  }

  newTask() {
    const dialogref = this.newTaskdialog.open(TaskNewDialogComponent, {
      width: '400px',
      data: {},
    });

    dialogref.afterClosed().subscribe((res) => {
      if (res === 'update') {
        this.buscarTarefas();
      }
    });
  }

  deleteTask(id: number) {
    this.taskService.deleteTask(id).subscribe({
      next: (data) => {
        this.tasks = this.tasks.filter((e) => e.id != id);

        this.snackBar.open(data.status, 'Fechar', {
          duration: 3000,
          horizontalPosition: 'center',
          verticalPosition: 'bottom',
        });
      },
      error: (err) => {
        this.snackBar.open('Erro ao deletar tarefa!', 'Fechar', {
          duration: 3000,
          horizontalPosition: 'center',
          verticalPosition: 'bottom',
        });
      },
    });
  }

  concludeTask(task: Task) {
    const dataToSent: TaskEdit = {
      description: task.description,
      title: task.title,
      priority: task.priority,
      deadline: task.deadline,
      status: 'CONCLUIDA',
      personId: task.personDto.id,
    };

    this.taskService.updateTask(dataToSent, task.id).subscribe({
      next: (data) => {
        this.tasks = this.tasks.filter((e) => e.id != task.id);
        this.snackBar.open('Tarefa concluída com êxito!', 'Fechar', {
          duration: 3000,
          horizontalPosition: 'center',
          verticalPosition: 'bottom',
        });
      },
      error: (err) => {
        this.snackBar.open('Erro ao atualizar tarefa!', 'Fechar', {
          duration: 3000,
          horizontalPosition: 'center',
          verticalPosition: 'bottom',
        });
      },
    });

    console.log('edit', task);
  }
}
