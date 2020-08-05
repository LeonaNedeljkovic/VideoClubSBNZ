import { Injectable } from '@angular/core';
import { Router, ActivatedRouteSnapshot, CanActivate } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AdminAuthGuard implements CanActivate{


    constructor(){
    }

    canActivate() {
        var loggedUser = JSON.parse(localStorage.getItem("currentUser"));
        if (loggedUser != null && loggedUser['role'] === "ROLE_ADMIN") {
            return true;
        }
        return false;
    }
}