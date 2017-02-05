/*.config(function($stateProvider, $urlRouterProvider) {
    $stateProvider.html5Mode(true).hashPrefix('!')

  // Ionic uses AngularUI Router which uses the concept of states
  // Learn more here: https://github.com/angular-ui/ui-router
  // Set up the various states which the app can be in.
  // Each state's controller can be found in controllers.js
  $stateProvider.html5Mode(true)
    
  

      .state('login', {
    url: '/page1',
    templateUrl: 'Login.html',
    controller: 'loginCtrl'
  })

  .state('mainmenu', {
    url: '/page2',
    templateUrl: 'templates/MainMenu.html',
    controller: 'mainCtrl'
  })

  .state('contacts', {
    url: '/page3',
    templateUrl: 'templates/Contacts.html',
    controller: 'contactsCtrl'
  })
  


$urlRouterProvider.otherwise('/page1')

  

});*/angular.module('app.routes', [])

