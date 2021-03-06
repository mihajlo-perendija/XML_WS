import { Component, OnInit, Input, SimpleChanges, OnChanges } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { RentalService } from 'src/app/services/rental.service';
import { RentalFront } from 'src/app/models/Rental.model';
import { VehicleService } from 'src/app/services/vehicle.service';
import { Car } from 'src/app/models/Car.model';

@Component({
  selector: 'app-customer-rentals-listing',
  templateUrl: './customer-rentals-listing.component.html',
  styleUrls: ['./customer-rentals-listing.component.css']
})
export class CustomerRentalsListingComponent implements OnInit, OnChanges {

  @Input() status: string;
  rentals = new Array<RentalFront>();
  pageNo: number;
  totalPages: number;

  constructor(private userService: UserService,
              private rentalService: RentalService,
              private vehicleService: VehicleService) { }

  ngOnInit() {
  }

  ngOnChanges(changes: SimpleChanges) {
    this.rentals = new Array<RentalFront>();
    this.getCustomerRentals(0);
  }

  getCustomerRentals(pageNo: number) {
    const customerId = this.userService.getUser()?.id;
    this.rentalService.getCustomerRentals(customerId, this.status, pageNo).subscribe(
      (data: any) => {
        data.content.forEach(rentalBack => {
          this.getVehicle(rentalBack.vehicleId).then((vehicle: Car) => {
            const rental = new RentalFront(rentalBack.id, vehicle, rentalBack.startTime,
               rentalBack.endTime, rentalBack.bundle, null, rentalBack.customerId, rentalBack.ownerId, rentalBack.company);
            this.rentals.push(rental);
          });
        });

        this.pageNo = data.pageNo;
        this.totalPages = data.totalPages;
      },
      (error) => {
        alert(error);
      }
    );
  }

  getVehicle(vehicleID: number) {
    let promise = new Promise((resolve, reject) => {
      this.vehicleService.getOne(vehicleID).subscribe(
        (data: any) => {
          const vehicle = data;
          resolve(vehicle);
        },
        (error) => {
          console.log(error);
          reject();
        }
      );
    });
    return promise;
  }

  showVehicle(id: number) {
    const params = '?vehicleID=' + id;
    window.open('/vehicle' + params, '_blank');
  }

}
