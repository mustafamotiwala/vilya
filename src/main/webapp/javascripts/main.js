var m = require ('mithril');
var Login = require('./login');
var _ = require('lodash');

var App = {
  controller: function () {
    this.isLoggedIn=false;
  },
  view: function (controller) {
    'use strict';
    if(!controller.isLoggedIn){
      return m.route('/login');
    }
    return [
      m('h1','PBX Home page.'),
      m('a[href="/login"]','Login')
    ];
  }
};

var routes = {
  '/': App,
  '/login': Login
};

m.route.mode = 'pathname';
m.route(document.body, '/', routes);
