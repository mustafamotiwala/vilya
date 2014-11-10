var m = require('mithril');
var utils = require('./utils');
var _ = require('lodash');

module.exports = {
  controller: function () {
    'use strict';
    this.username = m.prop('');
    this.password = m.prop('');

    this.login = function () {
      var notice = new utils.NotificationFX({
        message: '<span class="fa fa-2x fa-exclamation-triangle"></span><p>Username or password in correct.</p>',
        layout: 'bar',
        effect: 'slidetop',
        type: 'error'
      });
      notice.show();
    };
  },
  view: function (controller) {
    'use strict';
    return utils.container(function () {
      return m('.row[id="page-login"]', [
        m('.col-xs-12.col-md-4.col-md-offset-4.col-sm-6.col-sm-offset-3', [
          m('.box', [
            m('.box-content', [
              m('.text-center', [
                m('h3.page-header', 'PBX Login')
              ]),
              m('.form-group.input-group.input-group-lg', [
                m('span.input-group-addon',m('i.fa.fa-user')),
                m('input.form-control', {
                  name: 'username',
                  type: 'text',
                  onchange: m.withAttr('value', controller.username),
                  value: controller.username(),
                  placeholder: 'Username'
                })
              ]),
              m('.form-group.input-group.input-group-lg', [
                m('span.input-group-addon', m('i.fa.fa-key.fa-rotate-180')),
                m('input.form-control', {
                  name: 'password',
                  type: 'password',
                  onchange: m.withAttr('value', controller.password),
                  value: controller.password(),
                  placeholder: 'Password'
                })
              ]),
              m('.text-center', [
                m('button.btn.btn-primary.btn-3.btn-twin.icon.icon-arrow-right', {onclick: _.bind(controller.login, controller)},'Sign in')
              ])
            ])
          ])
        ])
      ]);
    });
  }
};
