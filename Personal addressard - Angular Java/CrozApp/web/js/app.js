//main app module with dependencies 
angular.module('app', ['ngRoute', 'ui.bootstrap', 'ngResource', 'app.controllers', 'app.directives', 'app.routes', 'app.services'])




        .config(function ($routeProvider) {


//The route engine with resolve methods
            //Resolve methods are allowing preloading of the data before view renderering
            $routeProvider
                    .when("/", {
                        templateUrl: "templates/Login.html",
                        controller: 'loginCtrl'
                    })
                    .when("/mainMenu/", {
                        templateUrl: "templates/MainMenu.html",
                        controller: 'mainCtrl'
                    })
                    .when("/contacts/", {
                        templateUrl: "templates/Contacts.html",
                        controller: 'contactsCtrl',
                        resolve: {
                            ContactServiceAllData: function (ContactServiceAll) {
                                return ContactServiceAll.promise;
                            }
                       }
                    })
                    .when("/countries/", {
                        templateUrl: "templates/Countries.html",
                        controller: 'countriesCtrl',
                        resolve: {
                            CountryServiceAllData: function (CountryServiceAll) {
                                return CountryServiceAll.promise;
                            }
                        }
                    })
                    .when("/cities/", {
                        templateUrl: "templates/Cities.html",
                        controller: 'citiesCtrl',                      
                        resolve: {
                            CityServiceAllData: function (CityServiceAll) {
                                return CityServiceAll.promise;
                            }
                        }
                    })
                    .when("/newContact/", {
                        templateUrl: "templates/NewContact.html",
                        controller: 'newContactCtrl',
                        resolve: {
                            ContactServiceData: function (ContactService) {
                                return ContactService.promise;
                            },
                            CityServiceData: function (CityService) {
                                return CityService.promise;
                            },
                        }
                    })
                    .when("/newCountry/", {
                        templateUrl: "templates/NewCountry.html",
                        controller: 'countryCtrl'
                    })
                    .when("/newCity/", {
                        templateUrl: "templates/NewCity.html",
                        controller: 'newCityCtrl',
                        resolve: {
                            AllCountriesData: function (AllCountries) {
                                return AllCountries.promise;
                            }
                        }
                    })
                    .when("/editContact", {
                        templateUrl: "templates/EditContact.html",
                        controller: 'contactCtrl',
                        resolve: {
                            ContactServiceData: function (ContactService) {
                                return ContactService.promise;
                            },
                            CityServiceData: function (CityService) {
                                return CityService.promise;
                            },
                        }
                    })
                    .when("/editCity", {
                        templateUrl: "templates/EditCity.html",
                        controller: 'cityCtrl',
                        resolve: {
                            CityService2Data: function (CityService2) {
                                return CityService2.promise;
                            },
                            CountriesServiceData: function (CountriesService) {
                                return CountriesService.promise;
                            }
                        }
                    })
                    .when("/editCountry", {
                        templateUrl: "templates/EditCountry.html",
                        controller: 'countryCtrl',
                        resolve: {
                            CountryServiceData: function (CountryService) {
                                return CountryService.promise;
                            }
                        }
                    })
                    .when("/about", {
                        templateUrl: "templates/About.html",
                        controller: 'aboutCtrl',
                    });
        });




    