import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface Task {
  id: number;
  title: string;
  description: string;
  deadline: string;
  createdAt: string;
  personDto: { id: number; name: string };
  priority: 'BAIXA' | 'MEDIA' | 'ALTA';
  status: 'EM_ANDAMENTO' | 'CONCLUIDA';
}

export interface TaskEdit {
  description: string;
  title: string;
  priority: 'BAIXA' | 'MEDIA' | 'ALTA';
  deadline: string;
  status: 'EM_ANDAMENTO' | 'CONCLUIDA';
  personId: number;
}

export interface TaskNew {
  personId: number;
  description: string;
  title: string;
  priority: string;
  deadline: string;
}

export interface Responsable {
  id: number;
  name: string;
}

export interface TaskRequestFilters {
  numero?: number | null;
  titulo?: string;
  responsavel?: string;
  situacao?: string;
}

@Injectable({
  providedIn: 'root',
})
export class TaskService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  getTasks(filters?: TaskRequestFilters): Observable<Task[]> {
    const token = sessionStorage.getItem('token');

    let params = new HttpParams();
    if (filters?.numero && filters?.numero != 0) {
      params = params.set('numero', filters.numero);
    }
    if (filters?.titulo?.length) {
      params = params.set('titulo', filters.titulo);
    }
    if (filters?.responsavel) {
      params = params.set('responsavel', filters.responsavel);
    }
    if (filters?.situacao) {
      params = params.set('situacao', filters.situacao);
    }

    return this.http.get<Task[]>(`${this.apiUrl}/tasks`, {
      params,
      headers: {
        Content: 'Application/JSON',
        Authorization: `Bearer ${token}`,
      },
    });
  }

  getResponsables(): Observable<Responsable[]> {
    const token = sessionStorage.getItem('token');
    return this.http.get<Responsable[]>(`${this.apiUrl}/persons`, {
      headers: {
        Content: 'Application/JSON',
        Authorization: `Bearer ${token}`,
      },
    });
  }

  updateTask(task: TaskEdit, id: number): Observable<{ status: string }> {
    const token = sessionStorage.getItem('token');


    console.log(task, id)

    return this.http.put<{ status: string }>(
      `${this.apiUrl}/tasks/${id}`,
      task,
      {
        headers: {
          Content: 'Application/JSON',
          Authorization: `Bearer ${token}`,
        },
      }
    );
  }


  newTask(task: TaskNew): Observable<{ status: string }> {
    const token = sessionStorage.getItem('token');

    return this.http.post<{ status: string }>(
      `${this.apiUrl}/tasks`,
      task,
      {
        headers: {
          Content: 'Application/JSON',
          Authorization: `Bearer ${token}`,
        },
      }
    );
  }

  deleteTask(id: number): Observable<{ status: string }> {
    const token = sessionStorage.getItem('token');

    return this.http.delete<{ status: string }>(
      `${this.apiUrl}/tasks/${id}`,
      {
        headers: {
          Content: 'Application/JSON',
          Authorization: `Bearer ${token}`,
        },
      }
    );
  }
}
