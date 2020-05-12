import { Component, OnInit, Input } from '@angular/core';
import { Car } from 'src/app/models/Car.model';
import { Rental } from 'src/app/models/Rental.model';

@Component({
  selector: 'app-sc-car-teaser',
  templateUrl: './sc-car-teaser.component.html',
  styleUrls: ['./sc-car-teaser.component.css']
})
export class ScCarTeaserComponent implements OnInit {

  @Input() bundles: any;
  @Input() rental: Rental;

  constructor() { }

  ngOnInit() {
  }

  addToBundle(bundle) {
    if ((bundle.owner) && bundle.owner !==  this.rental.car.ownerId){
      alert('Cant add to bundle bla bla bla... prettier alert needed');
      return;
    }

    bundle.owner = this.rental.car.ownerId;
    bundle.rentals.push(this.rental);
    this.rental.bundle = bundle.name;
  }

}