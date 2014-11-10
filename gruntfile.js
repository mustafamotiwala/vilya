module.exports = function(grunt) {
  'use strict';
  // Project Configuration
  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
    watch: {
      js: {
        files: ['src/main/webapp/javascripts/**/*.js'],
        tasks: ['jshint', 'browserify:dev', 'cssmin']
      },
      css: {
        files: ['src/main/webapp/css/**/*.css'],
        tasks: ['cssmin']
      },
      grunt: {
        files: ['gruntfile.js'],
        tasks: ['browserify:dev', 'cssmin', 'copy', 'jade']
      },
      browserify: {
        files: ['src/main/webapp/javascripts/**/*.js'],
        tasks: ['browserify:dev']
      },
      vendorjs: {
        files: ['src/main/webapp/javascripts/vendor/**/*.js'],
        tasks: ['copy']
      },
      images: {
        files: ['src/main/webapp/images/**'],
        tasks: ['copy:images']
      },
      jade: {
        files: ['src/main/webapp/**/*.jade'],
        tasks: ['jade']
      }
    }, // END watch
    jshint: {
      all: {
        src: ['gruntfile.js', 'src/main/webapp/javascripts/*.js'],
        options: {
          jshintrc: true
        }
      }
    },
    concurrent: {
      tasks: ['watch', 'browserify:dev', 'cssmin', 'copy', 'jade'],
      options: {
        logConcurrentOutput: true
      }
    },
    browserify: {
      dev: {
        options: {
          debug: true,
          exclude: ['src/main/webapp/javascripts/vendor/**/*.js']
        },
        files:{
          'src/main/resources/webapp/assets/js/app.js': [ 'src/main/webapp/javascripts/**/*.js' ]
        }
      },
      dist: {
        options: {
          debug: false,
          transform: ['uglifyify'],
          exclude: ['src/main/webapp/javascripts/vendor/**/*.js']
        },
        files: {
          'src/main/resources/webapp/assets/js/app.js': ['src/main/webapp/javascripts/**/*.js']
        }
      }
    },
    cssmin: {
      combine: {
        files: {
          'src/main/resources/webapp/assets/css/style.css': [
            'src/main/webapp/css/buttons.css',
            'src/main/webapp/css/style.css',
            'src/main/webapp/css/custom.css',
            'src/main/webapp/css/notification.css'
          ]
        }
      }
    },
    copy: {
      main: {
        expand: true,
        cwd: 'src/main/webapp/javascripts/',
        src: 'vendor/**',
        dest: 'src/main/resources/webapp/assets/js/'
      },
      images: {
        expand: true,
        cwd: 'src/main/webapp',
        src: 'images/**',
        dest: 'src/main/resources/webapp/assets/'
      }
    },
    jade: {
      compile: {
        pretty: false,
        options:{
          data: {
            debug: true
          }
        },
        files: {
          'src/main/resources/webapp/index.html': ['src/main/webapp/views/index.jade']
        }
      }
    }
  });

    //Load NPM tasks
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-contrib-jshint');
  grunt.loadNpmTasks('grunt-browserify');
  grunt.loadNpmTasks('grunt-nodemon');
  grunt.loadNpmTasks('grunt-concurrent');
  grunt.loadNpmTasks('grunt-contrib-cssmin');
  grunt.loadNpmTasks('grunt-contrib-copy');
  grunt.loadNpmTasks('grunt-contrib-jade');

  //Making grunt default to force in order not to break the project.
  grunt.option('force', true);

  //Default task(s).
  grunt.registerTask('default', ['jshint', 'concurrent']);

  //distribution task
  grunt.registerTask('dist', ['jshint', 'browserify:dist', 'cssmin', 'copy', 'jade']);

  //Test task.
  grunt.registerTask('test', ['env:test', 'mochaTest', 'karma:unit']);
};
