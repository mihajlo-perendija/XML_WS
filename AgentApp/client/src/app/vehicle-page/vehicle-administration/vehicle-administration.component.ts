import { VehicleService } from './../../services/vehicle.service';
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { trigger, transition, style, animate } from '@angular/animations';
import { SearchService } from 'src/app/services/search.service';
import { BrandService } from 'src/app/services/brand.service';
import { CategoryService } from 'src/app/services/category.service';
import { FuelService } from 'src/app/services/fuel.service';
import { TransmissionService } from 'src/app/services/transmission.service';

@Component({
  selector: 'app-vehicle-administration',
  templateUrl: './vehicle-administration.component.html',
  styleUrls: ['./vehicle-administration.component.css'],
  animations: [
    trigger(
      'inOutAnimation', 
      [
        transition(
          ':enter', 
          [
            style({ opacity: 0 }),
            animate('1s ease-out', 
                    style({opacity: 1 }))
          ]
        ),
        transition(
          ':leave', 
          [
            style({ opacity: 1 }),
            animate('1s ease-in', 
                    style({  opacity: 0 }))
          ]
        )
      ]
    )
  ]
})
export class VehicleAdministrationComponent implements OnInit {

  categories: Array<any> = [
    { id: 1560608769632, name: 'SUV' },
    { id: 1560608796014, name: 'Limousine' },
    { id: 1560608787815, name: 'Exotic' },
    { id: 1560608805101, name: 'Tank' }
  ];
  brands: Array<any> = [
    { id: 1560608769632, name: 'Mercedes' },
    { id: 1560608796014, name: 'BMW' },
    { id: 1560608787815, name: 'Nissan' },
    { id: 1560608805101, name: 'Toyota' }
  ];
  models: Array<any> = [
    { id: 1560608769632, name: 'R8' },
    { id: 1560608796014, name: 'M3' },
    { id: 1560608787815, name: 'Supra' },
    { id: 1560608805101, name: 'T30' }
  ];
  fuels: Array<any> = [
    { id: 1560608769632, name: 'Gas' },
    { id: 1560608796014, name: 'Diesel' },
    { id: 1560608787815, name: 'Electric' },
    { id: 1560608805101, name: 'Coal' }
  ];
  transmissions: Array<any> = [
    { id: 1560608769632, name: 'Automatic' },
    { id: 1560608796014, name: 'Manual 4 speed' },
    { id: 1560608787815, name: 'Manual 5 speed' },
    { id: 1560608805101, name: 'Manual 6 speed' }
  ];

  vehicle: Object = null;
  vehicleCancelBackup: Object = null;

  private sub: any;
  form: FormGroup;
  edditing: boolean = false;
  now: Date = new Date();
  images: any;

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    private formBuilder: FormBuilder,
    private searchService: SearchService,
    private brandService: BrandService,
    private categoryService: CategoryService,
    private fuelService: FuelService,
    private trannsmissionService: TransmissionService,
    private vehicleService: VehicleService) {


  }

  ngOnInit() {
    this.sub = this.activatedRoute.params.subscribe((params) => {
      
      //var vehicle_id = params.id;

      //change form with vehicle
      // this.form.setValue({
      //   brand:  1560608796014,
      //   model: 1560608769632,
      //   category: 1560608805101,
      //   transmission: 1560608805101,
      //   fuel: 1560608805101,
      //   mileage: 30000,
      //   seats: 3,
      //   alwaysAvailable: true,
      //   availableFrom: [''],
      //   availableUntil: [''],
      // });
    });
    this.form = this.formBuilder.group({
      brand: ['', [Validators.required]],
      model: ['', [Validators.required]],
      category: ['', [Validators.required]],
      transmission: ['', [Validators.required]],
      fuel: ['', [Validators.required]],
      mileage: ['', [Validators.required]],
      seats: ['', [Validators.required]],
      alwaysAvailable: [false],
      availableFrom: [''],
      availableUntil: [''],
      unlimitedMileage: [false],
      alowedMileage: [''],
      cdw: [false],
    });
    this.brandService.getAll().subscribe(
      (data: any) => {
        this.brands = data;
      },
      (error) => {
        alert(error);
      }
    );
    this.categoryService.getAll().subscribe(
      (data: any) => {
        this.categories = data;
      },
      (error) => {
        alert(error);
      }
    );
    this.fuelService.getAll().subscribe(
      (data: any) => {
        this.fuels = data;
      },
      (error) => {
        alert(error);
      }
    );
    this.trannsmissionService.getAll().subscribe(
      (data: any) => {
        this.transmissions = data;
      },
      (error) => {
        alert(error);
      }
    );

  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

  brandSelectionChanged(selectedBrands) {
    this.models = selectedBrands.value.models;
  }

  onSubmit(data) {
    console.log(this.form.controls);
    var vehicle1 = {
      brand:this.form.controls.brand.value,
      model: this.form.controls.model.value,
      category: this.form.controls.category.value,
      seats: 5,
      transmission: this.form.controls.transmission.value,
      fuel: this.form.controls.fuel.value,
      pricelist: null,
      childSeats: 2,
      mileage: 200,
      cdw: 10,
      numberOfStars: 0,
      numberOfReviews: 0,
      locationLongitude: 0,
      locationLatitude: 0, 
      ownerId: 1
  }
  var vehicle = {
    brand: {
      id: this.form.controls.brand.value.id,
      name: this.form.controls.brand.value.name
    },
    model: {
      id: this.form.controls.model.value.id,
      name: this.form.controls.model.value.name
    },
    category: {
      id: this.form.controls.category.value.id,
      name: this.form.controls.category.value.name
    },
    seats: this.form.controls.seats.value,
    transmission:{
      id: this.form.controls.transmission.value.id,
      name: this.form.controls.transmission.value.name
    },
    fuel: {
      id: this.form.controls.fuel.value.id,
      name: this.form.controls.fuel.value.name
    },
    pricelist: null,
    childSeats: 2,
    mileage: 200,
    cdw: 10,
    numberOfStars: 0,
    numberOfReviews: 0,
    locationLongitude: 0,
    locationLatitude: 0, 
    ownerId: 1
}
console.log(vehicle);
    this.vehicleService.addVehicle(vehicle,this.images.files).subscribe(
      (data: any) => { 
        alert("Vehicle created");
        this.ngOnInit();
        },
      (error) => { alert(error);  }
    );
  }
  doAction(){
    // this.dialogRef.close({event:this.action,data:this.local_data});
  }
}