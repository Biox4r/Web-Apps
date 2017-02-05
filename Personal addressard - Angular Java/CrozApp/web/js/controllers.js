angular.module('app.controllers', [])




//Login controller 
        .controller('loginCtrl', ['$scope', '$http', '$location',
            function ($scope, $http, $location) {


                $scope.obj = {};
                $scope.token = [];
                var user = $scope.username;
                var pass = $scope.username;

                $scope.login = function () {
                    alert("login controller called");
                    console.log($scope.obj.username);
                    console.log($scope.obj.password);

//HTTP method for login,
                    $http({
                        method: 'POST',
                        url: 'http://localhost:8080/CrozApp/JSPLogin',
                        //url: 'http://10.0.2.2:8080/DocBackend/JSP',
                        data: {username: $scope.obj.username, password: $scope.obj.password},
                        headers: {'Content-Type': 'application/json'}
                    }).success(function (data, status, headers, config) {


                        //if backend returns approval for loging in, we are getting back token in JSON format
                        //The usage of the token is not applied for loging system
                        //Token can be stored in database or as a cookie if needed.

                        $scope.token = headers("token");
                        console.log(headers("token"));
                        if (data.token != null) {
                            alert("login sucess");
                            $location.path("/mainMenu");
                        } else if (data.token == null) {
                            alert("Check your credentials");
                        }

                    });
                    console.log("POST done");
                };
            }])
//Main controller
        .controller('mainCtrl', ['$scope', '$location', '$window', // The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
            function ($scope, $location, $window) {

                $scope.goContacts = function () {
                    $location.path("/contacts");
                    $window.location.reload(true)
                };
                $scope.goCountries = function () {
                    $location.path("/countries");
                    $window.location.reload(true)
                };
                $scope.goCities = function () {
                    $location.path("/cities");
                    $window.location.reload(true)
                };
                $scope.goAbout = function () {
                    $location.path("/about");
                    $window.location.reload(true)
                };
                $scope.Exit = function () {
                    if (confirm("Do you really want to exit application?") == true) {
                        $window.location = "http://www.google.hr";
                    } else {

                    }
                };
            }])
//About controller 
        .controller('aboutCtrl', ['$scope', '$location', // The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
            function ($scope, $location, $http) {
                //console.log($scope);
                //var jsonDataStr = JSON.stringify(params);

            }])
//Contact controller 
        .controller('contactCtrl', ['$scope', '$http', '$location', '$window', 'ContactService', 'CityService',
            function ($scope, $http, $location, $window, ContactService, CityService) {
                $scope.contact = [];
                $scope.contact = ContactService.getCon();
                $scope.chooseCities = [];
                $scope.chooseCities = CityService.getCities();
                $scope.gender = [{id: 1, name: 'male'}, {id: 2, name: 'female'}];
                var tempCityId = null;
                var tempGenderId = null;

//Setting the town in the dropbox on view loading according to the id of the town that is binded to user
//We select city from city list that matches the city in which Contact lives
//So this filter has been applied:
                function setSelectedContact() {
                    var city = $scope.chooseCities.filter(function (city) {
                        return city.id === $scope.contact[1].city_id;
                    })[0];

                    if (city) {
                        $scope.selectedValue1 = city.id.toString();
                    }
                }

                setSelectedContact();


//Setting the gender in the dropbox on view loading according to the id of the gender that is binded to user
//We select gender from gender(sex) list that matches the gender of Contact
//So this filter has been applied:
                function setSelectedGender() {
                    var gen = $scope.gender.filter(function (gen) {
                        return gen.id === $scope.contact[4].id;
                    })[0];

                    if (gen) {
                        $scope.selectedValue3 = gen.id.toString();
                    }
                }

                setSelectedGender();

//Get city id from city dropbox
                $scope.selectCity = function (city) {
                    tempCityId =
                            tempCityId = JSON.stringify(city.id);
                    console.log("country id " + tempCityId)
                }
//Get gender id from gender dropbox
                $scope.selectGender = function (gen) {
                    tempGenderId = gen.id;
                    console.log("country id " + tempGenderId)
                }

//HTTP method for contact update
//Note - we are passing contact from NG repeat inside our HTML view 

                $scope.updateContact = function (contact) {

                    $http({
                        method: 'POST',
                        url: 'http://localhost:8080/CrozApp/JSPContact',
                        //url: 'http://10.0.2.2:8080/DocBackend/JSP',
                        data: {action: 'updateContact', contact: contact, city_id: tempCityId, gender_id: tempGenderId},
                        headers: {'Content-Type': 'application/json'}
                    }).success(function (data, status, headers, config) {
                        //console.log(data);
                        if (data.status == 1) {
                            alert("Successful update");

                        } else if (data.status == 2) {
                            alert("There was a problem with updateing");
                        }
                    });


                };
                //Method for contact removal              
                $scope.deleteContact = function (contact) {

                    $http({
                        method: 'POST',
                        url: 'http://localhost:8080/CrozApp/JSPContact',
                        //url: 'http://10.0.2.2:8080/DocBackend/JSP',
                        data: {action: 'deleteContact', contact: contact},
                        headers: {'Content-Type': 'application/json'}
                    }).success(function (data, status, headers, config) {
                        //console.log(data);
                        if (data.status == 1) {
                            alert("Successful delete");

                        } else if (data.status == 2) {
                            alert("There was a problem with deleting");
                        }
                    });



                };

//Go to table with contacts
                $scope.goContacts = function () {
                    $location.path("\contacts");
                    $window.location.reload(true)
                }

            }])

//Controller for new contact
        .controller('newContactCtrl', ['$scope', '$http', '$location', '$window', 'ContactService', 'CityService',
            function ($scope, $http, $location, $window, ContactService, CityService) {
                $scope.contact = [];
                $scope.contact = ContactService.getCon();
                $scope.chooseCities = [];
                $scope.chooseCities = CityService.getCities();
                $scope.gender = [{id: 1, name: 'male'}, {id: 2, name: 'female'}];
                $scope.con = {};

                var tempCityId = null;
                var tempGenderId = null;

//Get city id from city dropbox
                $scope.selectCity = function (city) {
                    tempCityId = city.id;
                    console.log("country id " + tempCityId)
                }

//Get gender id from gender drop box
                $scope.selectGender = function (gen) {
                    tempGenderId = gen.id;
                    console.log("country id " + tempGenderId)
                }

                $scope.insertContact = function () {

                    $http({
                        method: 'POST',
                        url: 'http://localhost:8080/CrozApp/JSPContact',
                        //url: 'http://10.0.2.2:8080/DocBackend/JSP',
                        data: {action: 'insertContact', first_name: $scope.con.first_name, last_name: $scope.con.last_name, phone: $scope.con.phone, email: $scope.con.email, street: $scope.con.street, street_no: $scope.con.street_no, city_id: tempCityId, gender_id: tempGenderId},
                        headers: {'Content-Type': 'application/json'}
                    }).success(function (data, status, headers, config) {
                        //console.log(data);
                        if (data.status == 1) {
                            alert("Successful insert");

                        } else if (data.status == 2) {
                            alert("There was a problem with inserting");
                        }
                    });

                };

//Go to table with contacts
                $scope.goContacts = function () {
                    $location.path("\contacts");
                    $window.location.reload(true)
                }

            }])


//This service is getting contact details based on the passed id from our contacts table after clicking edit button 
//The id is pulled out from local storage
//The http method is calling JAVA servlet JSPContact to acquire the data
        .service('ContactService', function ($http) {
            var conData = null;
            var promise = $http({
                method: 'POST',
                url: 'http://localhost:8080/CrozApp/JSPContact',
                //url: 'http://10.0.2.2:8080/DocBackend/JSP',
                data: {action: 'getContact', id: localStorage.getItem("conid")},
                headers: {'Content-Type': 'application/json'}
            }).success(function (data) {
                conData = data;
            });

            return {
                promise: promise,
                setData: function (data) {
                    conData = data;
                },
                getCon: function () {
                    return conData;//.getSomeData();
                }
            };
            localStorage.clear();
        })
//This service is pulling out all cities from database  
//The http method is calling JAVA servlet JSPCities to acquire the data
        .service('CityService', function ($http) {
            var citiesData = null;
            var promise = $http({
                method: 'POST',
                url: 'http://localhost:8080/CrozApp/JSPCities',
                //url: 'http://10.0.2.2:8080/DocBackend/JSP',
                data: {action: 'allCities'},
                headers: {'Content-Type': 'application/json'}
            }).success(function (data) {
                citiesData = data;
            });

            return {
                promise: promise,
                setData: function (data) {
                    citiesData = data;
                },
                getCities: function () {
                    return citiesData;//.getSomeData();
                }
            };
        })

        //This service is pulling out all the data from table SEX, which represents the gender of Contact
        //
        .service('GenderService', function ($http) {
            var citiesData = null;
            var promise = $http({
                method: 'POST',
                url: 'http://localhost:8080/CrozApp/JSPContact',
                //url: 'http://10.0.2.2:8080/DocBackend/JSP',
                data: {action: 'getGender'},
                headers: {'Content-Type': 'application/json'}
            }).success(function (data) {
                citiesData = data;
            });

            return {
                promise: promise,
                setData: function (data) {
                    citiesData = data;
                },
                getGender: function () {
                    return citiesData;//.getSomeData();
                }
            };
        })

//Controller for City template
        .controller('cityCtrl', ['$scope', '$http', '$location', '$window', 'CityService2', 'CountriesService', // The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
            function ($scope, $http, $location, $window, CityService2, CountriesService) {
                $scope.coun = [];
                $scope.coun = CountriesService.getData();
                $scope.city = [];
                $scope.city = CityService2.getCity();

                var tempCountryId = null;

                function setSelectedCity() {

                    var cnt = $scope.coun.filter(function (cnt) {
                        return cnt.id === $scope.city[0].country_id;
                    })[0];

                    if (cnt) {
                        $scope.selected = cnt.id.toString();
                    }
                }

                setSelectedCity();
//activation of filter

                $scope.selectCity = function (cnt) {
                    tempCountryId = cnt.id;
                    console.log("country id " + tempCountryId)
                }

//HTTP method for city update
                $scope.updateCity = function (city) {

                    $http({
                        method: 'POST',
                        url: 'http://localhost:8080/CrozApp/JSPCities',
                        //url: 'http://10.0.2.2:8080/DocBackend/JSP',
                        data: {action: 'updateCity', city: city, country_id: tempCountryId},
                        headers: {'Content-Type': 'application/json'}
                    }).success(function (data, status, headers, config) {
                        //console.log(data);
                        if (data.status == 1) {
                            alert("Successful update");

                        } else if (data.status == 2) {
                            alert("There was a problem with updating");
                        }
                    });


                };
//HTTP method for city removal

                $scope.deleteCity = function (city) {



                    $http({
                        method: 'POST',
                        url: 'http://localhost:8080/CrozApp/JSPCities',
                        //url: 'http://10.0.2.2:8080/DocBackend/JSP',
                        data: {action: 'deleteCity', city: city},
                        headers: {'Content-Type': 'application/json'}
                    }).success(function (data, status, headers, config) {
                        //console.log(data);
                        if (data.status == 1) {
                            $location.path("\cities");
                            alert("Successfuly removed");
                        } else if (data.status == 2) {
                            alert("There was a problem with removal");
                        } else if (data.status == 3) {
                            alert("You can't delete child table - constraint violation (please contact DB administrator");
                        }
                    });

                };
//Go to table with all cities
                $scope.goCities = function () {
                    $location.path("\cities");
                    $window.location.reload(true);
                }

            }]

                )
//Controller for new city template
        .controller('newCityCtrl', ['$scope', '$http', '$location', '$window', 'AllCountries', // The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
            function ($scope, $http, $location, $window, AllCountries) {

                //Filling out the data from All countries service.
                $scope.coun2 = [];
                $scope.coun2 = AllCountries.getAllCon();

// scope object that we use for binding with variables in html view
                $scope.cityObj = {};
                var tempCountryId = null;



//method for selecting city id from drop down menu
                $scope.selectCity = function (cnt) {
                    tempCountryId = cnt.id;
                    console.log("country id " + tempCountryId)
                }
//method for city insertion
                $scope.insertCity = function () {


                    $http({
                        method: 'POST',
                        url: 'http://localhost:8080/CrozApp/JSPCities',
                        //url: 'http://10.0.2.2:8080/DocBackend/JSP',
                        data: {action: 'insertCity', name: $scope.cityObj.name, zip_code: $scope.cityObj.zip_code, country_id: tempCountryId},
                        headers: {'Content-Type': 'application/json'}
                    }).success(function (data, status, headers, config) {
                        //console.log(data);
                        if (data.status == 1) {
                            alert("Successful insert");

                        } else if (data.status == 2) {
                            alert("There was a problem with inserting");
                        }
                    });

                };

                //Go to main template for cities
                $scope.goCities = function () {
                    $location.path("\cities");
                    $window.location.reload(true);
                }
            }]

                )

//Service that pulls out data for specific city from database
        .service('CityService2', function ($http) {
            var cityData = null;

            var promise = $http({
                method: 'POST',
                url: 'http://localhost:8080/CrozApp/JSPCities',
                //url: 'http://10.0.2.2:8080/DocBackend/JSP',
                data: {action: 'getCity', id: localStorage.getItem("citid")},
                headers: {'Content-Type': 'application/json'}
            }).success(function (data) {
                cityData = data;
            });

            return {
                promise: promise,
                setData: function (data) {
                    cityData = data;
                },
                getCity: function () {
                    return cityData;//.getSomeData();
                }
            };
            localStorage.clear();
        })
//Service for pulling out all countries from database
        .service('CountriesService', function ($http) {
            var countryData = null;

            var promise = $http({
                method: 'POST',
                url: 'http://localhost:8080/CrozApp/JSPCountries',
                //url: 'http://10.0.2.2:8080/DocBackend/JSP',
                data: {action: 'allCountries'},
                headers: {'Content-Type': 'application/json'}
            }).success(function (data) {
                countryData = data;
            });

            return {
                promise: promise,
                setData: function (data) {
                    countryData = data;
                },
                getData: function () {
                    return countryData;//.getSomeData();
                }
            };

        })
//Service for pulling out all countries from database
        .service('AllCountries', function ($http) {
            var cityData = null;

            var promise = $http({
                method: 'POST',
                url: 'http://localhost:8080/CrozApp/JSPCountries',
                //url: 'http://10.0.2.2:8080/DocBackend/JSP',
                data: {action: 'allCountries'},
                headers: {'Content-Type': 'application/json'}
            }).success(function (data) {
                cityData = data;
            });

            return {
                promise: promise,
                setData: function (data) {
                    cityData = data;
                },
                getAllCon: function () {
                    return cityData;//.getSomeData();
                }
            };

        })


//Contact controller
        .controller('contactsCtrl', ['$scope', '$http', '$location', '$window', 'ContactServiceAll', // The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
            function ($scope, $http, $location, $window, ContactServiceAll) {

                //scope array for all contacts
                $scope.allcontacts = [];
                $scope.allcontacts = ContactServiceAll.getContacts();


                //This part of the code below is haveing impact on tables look and form 
//Paganation is applied which is allowing us to iterate through table view through table pages.
                $scope.reverse = true;
                $scope.currentPage = 1;
                $scope.order = function (predicate) {
                    $scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse : false;
                    $scope.predicate = predicate;
                };

                $scope.totalItems = $scope.allcontacts.length;
                $scope.numPerPage = 5;
                $scope.paginate = function (value) {
                    var begin, end, index;
                    begin = ($scope.currentPage - 1) * $scope.numPerPage;
                    end = begin + $scope.numPerPage;
                    index = $scope.allcontacts.indexOf(value);
                    return (begin <= index && index < end);
                };

//This method is getting contact id which is passed then to local storage for later usage
                $scope.setConId = function (contact) {
                    var id = JSON.stringify(contact.id);
                    localStorage.setItem("conid", id);
                    $location.path("/editContact");
                    //$scope.editContact();
                }
//Go to main menu 
                $scope.goMain = function () {
                    $location.path("/mainMenu");
                    //$window.location method is used to allow complete route reload and controller reload
                    //This allows us to get newly created data.
                    $window.location.reload(true);
                };
//Go to table with all contacts
                $scope.goContacts = function () {
                    $location.path("/contacts");
                    $window.location.reload(true);
                };

//Open template for new contact
                $scope.newContact = function () {
                    $location.path("/newContact");
                    $window.location.reload(true);
                };


            }]
                )

//Service for pulling out all contacts from database
        .service('ContactServiceAll', function ($http) {
            var conData = null;
            var promise = $http({
                method: 'POST',
                url: 'http://localhost:8080/CrozApp/JSPContact',
                //url: 'http://10.0.2.2:8080/DocBackend/JSP',
                data: {action: 'allContacts'},
            }).success(function (data) {
                conData = data;
            });
            return {
                promise: promise,
                setData: function (data) {
                    conData = data;
                },
                getContacts: function () {
                    return conData;//.getSomeData();
                }
            };
            localStorage.clear();
        })
//Cities controller
        .controller('citiesCtrl', ['$scope', '$http', '$location', '$window', 'CityServiceAll', // The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
            function ($scope, $http, $location, $window, CityServiceAll) {
//scope array for all cities
                $scope.cities = [];
                $scope.cities = CityServiceAll.getCities();

                //This part of the code below is haveing impact on tables look and form 
//Paganation is applied which is allowing us to iterate through table view through table pages.
                $scope.predicate = 'name';
                $scope.reverse = true;
                $scope.currentPage = 1;
                $scope.order = function (predicate) {
                    $scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse : false;
                    $scope.predicate = predicate;
                };

                $scope.totalItems = $scope.cities.length;
                $scope.numPerPage = 5;
                $scope.paginate = function (value) {
                    var begin, end, index;
                    begin = ($scope.currentPage - 1) * $scope.numPerPage;
                    end = begin + $scope.numPerPage;
                    index = $scope.cities.indexOf(value);
                    return (begin <= index && index < end);
                };
//This method is getting city id which is passed then to local storage for later usage
                $scope.setCityId = function (city) {
                    var id = city.id;
                    localStorage.setItem("citid", id);
                    $location.path("/editCity");
                    $window.location.reload(true);
                }
//Method for opening Edit City template
                $scope.editCity = function () {
                    $location.path("/editCity");
                    $window.location.reload(true);
                };

                $scope.goMain = function () {
                    $location.path("/mainMenu");
                    $window.location.reload(true);
                };

                $scope.newCity = function () {
                    $location.path("/newCity");
                    $window.location.reload(true);
                };


            }]
                )
//Service that pulls out all cities from database
        .service('CityServiceAll', function ($http) {
            var conData = null;
            var promise = $http({
                method: 'POST',
                url: 'http://localhost:8080/CrozApp/JSPCities',
                //url: 'http://10.0.2.2:8080/DocBackend/JSP',
                data: {action: 'allCities'},
            }).success(function (data) {
                conData = data;
            });
            return {
                promise: promise,
                setData: function (data) {
                    conData = data;
                },
                getCities: function () {
                    return conData;//.getSomeData();
                }
            };

        })
//Controller for country template
        .controller('countryCtrl', ['$scope', '$http', '$location', '$window', 'CountryService', // The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
            function ($scope, $http, $location, $window, CountryService) {
                $scope.status = [];
                $scope.count = [];
                $scope.count = CountryService.getCountry();
                $scope.obj = {};
//HTTP method for country insertion
                $scope.insertCountry = function () {

                    $http({
                        method: 'POST',
                        url: 'http://localhost:8080/CrozApp/JSPCountries',
                        //url: 'http://10.0.2.2:8080/DocBackend/JSP',
                        data: {action: 'insertCountry', name: $scope.obj.name, alpha_2: $scope.obj.alpha_2, alpha_3: $scope.obj.alpha_3},
                        headers: {'Content-Type': 'application/json'}
                    }).success(function (data, status, headers, config) {
                        $scope.status = headers("status");
                        //console.log(data);
                        if (data.status == 1) {
                            alert("Sucessfull Insertion");
                            $location.path("/countries");

                        } else if (data.status == 2) {
                            alert("There was a problem with data insertion");
                        }
                    });

                };
//HTTP method for country update
                $scope.updateCountry = function (count) {
                    $http({
                        method: 'POST',
                        url: 'http://localhost:8080/CrozApp/JSPCountries',
                        //url: 'http://10.0.2.2:8080/DocBackend/JSP',
                        data: {action: 'updateCountry', country: count},
                        headers: {'Content-Type': 'application/json'}
                    }).success(function (data, status, headers, config) {
                        $scope.status = headers("status");
                        //console.log(data);
                        if (data.status == 1) {
                            alert("Sucessfull update");
                            //$location.path("/countries");                      
                        } else if (data.status == 2) {
                            alert("There was a problem with data insertion");
                        }
                    });
                };
//HTTP method for country removal
                $scope.deleteCountry = function (count) {


                    $http({
                        method: 'POST',
                        url: 'http://localhost:8080/CrozApp/JSPCountries',
                        //url: 'http://10.0.2.2:8080/DocBackend/JSP',
                        data: {action: 'deleteCountry', country: count},
                        headers: {'Content-Type': 'application/json'}
                    }).success(function (data, status, headers, config) {

                        $scope.status = headers("status");
                        //console.log(data);
                        if (data.status == 1) {
                            alert("Country was sucesfully deleted");

                        } else if (data.status == 2) {
                            alert("There was a problem with deleting");
                        }
                    });

                };
//Go to table with all countries
                $scope.goCountries = function () {
                    $location.path("/countries");
                    $window.location.reload(true);
                };
            }]

                )
//Service that allows us to get specific country by its ID
        .service('CountryService', function ($http) {
            var countryData = null;
            var promise = $http({
                method: 'POST',
                url: 'http://localhost:8080/CrozApp/JSPCountries',
                //url: 'http://10.0.2.2:8080/DocBackend/JSP',
                data: {action: 'getCountry', id: localStorage.getItem("countryid")},
                headers: {'Content-Type': 'application/json'}
            }).success(function (data) {
                countryData = data;
            });

            return {
                promise: promise,
                setData: function (data) {
                    countryData = data;
                },
                getCountry: function () {
                    return countryData;//.getSomeData();
                }
            };
        })
//Controller for countries template
        .controller('countriesCtrl', ['$scope', '$http', '$location', '$window', 'CountryServiceAll', // The following is the constructor function for this page's controller. See https://docs.angularjs.org/guide/controller
// You can include any angular dependencies as parameters for this function
// TIP: Access Route Parameters for your page via $stateParams.parameterName
            function ($scope, $http, $location, $window, CountryServiceAll) {
                /*$scope.reload = function () {
                 $window.location.reload(true);
                 }*/

                //reload();

                $scope.countries = [];
                $scope.countries = CountryServiceAll.getCountries();


                $scope.predicate = 'name';
                $scope.reverse = true;
                $scope.currentPage = 1;
                $scope.order = function (predicate) {
                    $scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse : false;
                    $scope.predicate = predicate;
                };

                $scope.totalItems = $scope.countries.length;
                $scope.numPerPage = 6;
                $scope.paginate = function (value) {
                    var begin, end, index;
                    begin = ($scope.currentPage - 1) * $scope.numPerPage;
                    end = begin + $scope.numPerPage;
                    index = $scope.countries.indexOf(value);
                    return (begin <= index && index < end);
                };

                $scope.setCountryId = function (country) {
                    var id = JSON.stringify(country.id);
                    localStorage.setItem("countryid", id);
                    $location.path("/editCountry");
                    $window.location.reload(true);
                    //$scope.editContact();
                }

                $scope.goMain = function ($route) {
                    $location.path("/mainMenu");
                    $window.location.reload(true);
                };

                $scope.newCountry = function () {
                    $location.path("/newCountry");
                    $window.location.reload(true);
                };

                $scope.edit = function () {

                };

                $scope.remove = function () {

                };


            }]

                )
//Service that gets all countries from database
        .service('CountryServiceAll', function ($http) {
            var conData = null;
            var promise = $http({
                method: 'POST',
                url: 'http://localhost:8080/CrozApp/JSPCountries',
                //url: 'http://10.0.2.2:8080/DocBackend/JSP',
                data: {action: 'allCountries'},
            }).success(function (data) {
                conData = data;
            });

            return {
                promise: promise,
                setData: function (data) {
                    conData = data;
                },
                getCountries: function () {
                    return conData;//.getSomeData();
                }
            };
            localStorage.clear();
        })



 