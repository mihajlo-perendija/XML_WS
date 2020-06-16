import { ReviewComponent } from './../../shared/review/review.component';
import { Component, OnInit, Input } from '@angular/core';
import { Car } from 'src/app/models/Car.model';
import { environment } from 'src/environments/environment';
import { CookieService } from 'ngx-cookie-service';
import { ShoppingCart } from 'src/app/models/ShoppingCart.model';
import { RentalFront } from 'src/app/models/Rental.model';

@Component({
  selector: 'app-car-teaser',
  templateUrl: './car-teaser.component.html',
  styleUrls: ['./car-teaser.component.css']
})
export class CarTeaserComponent implements OnInit {
  @Input() car: Car;
  @Input() from: number;
  @Input() to: number;
  API_URL = environment.API_URL;

  constructor(private cookieService: CookieService) {
  }

  ngOnInit() {
  }

  checkMileage(mileage) {
    return mileage != -1 ? mileage : "Unlimited";
  }

  inCart() {
    if (this.cookieService.get('shopping-cart')) {
      let cart = JSON.parse(this.cookieService.get('shopping-cart'));
      if (cart.rentals) {
        return cart.rentals.some(e => e.car.id === this.car.id);
      } else {
        return false;
      }
    }
    return false;
  }

  addToCart($event) {
    $event.stopPropagation();

    //TODO start - end time
    this.from = 1592406000;
    this.to = 1592578800;

    if (this.cookieService.get('shopping-cart')) {
      let cart = JSON.parse(this.cookieService.get('shopping-cart'));

      if (!cart.rentals.some(e => e.car.id === this.car.id)) {
        let rental = new RentalFront(null, this.car, this.from, this.to, null);
        cart.rentals.push(rental);
      }
      this.cookieService.set('shopping-cart', JSON.stringify(cart));
    } else {
      let cart = new ShoppingCart(new Array(), new Array());

      let rental = new RentalFront(null, this.car, this.from, this.to, null);
      cart.rentals.push(rental);
      this.cookieService.set('shopping-cart', JSON.stringify(cart));
    }
    //this.cookieService.delete('shopping-cart');

  }
}