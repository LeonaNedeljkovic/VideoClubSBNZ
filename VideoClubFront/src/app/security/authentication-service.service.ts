import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Observable} from 'rxjs';
import { map } from "rxjs/operators";
import { JwtUtilsService } from './jwt-utils.service';
import { RegistedUser } from '../model/registered-user.model';
import { Message } from '../dto/message';


@Injectable()
export class AuthenticationService {

  private readonly loginPath = "/auth/login";
  private readonly getUser = "/auth/";

  constructor(private http: HttpClient, private jwtUtilsService: JwtUtilsService) { }

  

  login(username: string, password: string) : Observable<any> {
    var headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<any>(this.loginPath, JSON.stringify({ username, password }), { headers }).pipe(
      map( ((res: any) => {
          let token = res && res['accessToken'];
          let message2 = res && res['message'];
          if (token) {
            localStorage.setItem('currentUser', JSON.stringify({
              role: res['role'],
              token: res['accessToken']
            }));
          }
          if(message2){
            console.log(res['message']);
            console.log(res['result']);
            localStorage.setItem('message2', JSON.stringify({
              message: res['message'],
              result: res['result']
            }));
            
          }  
      }) 
      )  
      );
  }

  getToken(): String {
    var currentUser = JSON.parse(localStorage.getItem('currentUser'));
    
    if (currentUser!==null){
      return currentUser['token'];
    }
   
    return "";
  }

  logout(): void {
    localStorage.removeItem('currentUser');
  }

  isLoggedIn(): boolean {
    if (this.getToken() != '') return true;
    else return false;
  }

  getCurrentUser() {
    if (localStorage.currentUser) {
      return JSON.parse(localStorage.currentUser);
    }
    else {
      return undefined;
    }
  }

  getUserInfo = (): Observable<RegistedUser> => {
    return this.http.get<RegistedUser>("/auth/user").pipe(
      map( (res: any) => {
          return res;
      })  );
  }

}
