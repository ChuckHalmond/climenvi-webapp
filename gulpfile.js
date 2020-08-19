"use strict";

// Load plugins
const autoprefixer = require("gulp-autoprefixer");
const cleanCSS = require("gulp-clean-css");
const del = require("del");
const gulp = require("gulp");
const header = require("gulp-header");
const merge = require("merge-stream");
const plumber = require("gulp-plumber");
const rename = require("gulp-rename");
const browsersync = require("browser-sync");
const sass = require("gulp-sass");
const uglify = require("gulp-uglify");

// Load package.json for banner
const pkg = require("./package.json");

// Resources, templates and vendor directories path
const paths = {
	resourcesDir: "./src/main/resources/static/sbadmin2",
	templatesDir: "./src/main/resources/templates",
	vendorDir: "./src/main/resources/static/vendor"
};

// Set the banner content
const banner = ["/*!\n",
	" * Start Bootstrap - <%= pkg.title %> v<%= pkg.version %> (<%= pkg.homepage %>)\n",
	" * Copyright 2013-" + (new Date()).getFullYear(), " <%= pkg.author %>\n",
	" * Licensed under <%= pkg.license %> (https://github.com/BlackrockDigital/<%= pkg.name %>/blob/master/LICENSE)\n",
	" */\n",
	"\n"
].join("");

// Clean vendor directory
function clean() {
	return del([paths.vendorDir]);
}

// Bring third party dependencies from node_modules into vendor directory
function modules() {
	
	// Bootstrap JS
	var bootstrapJS = gulp
		.src("./node_modules/bootstrap/dist/js/*")
		.pipe(gulp.dest(paths.vendorDir + "/bootstrap/js"));
	  
	// Bootstrap SCSS
	var bootstrapSCSS = gulp
		.src("./node_modules/bootstrap/scss/**/*")
		.pipe(gulp.dest(paths.vendorDir + "/bootstrap/scss"));
	 
	// Bootstrap Select js
	var bootstrapSelectJs = gulp
		.src("./node_modules/bootstrap-select/dist/js/bootstrap-select.min.js")
		.pipe(gulp.dest(paths.vendorDir + "/bootstrap-select/js"));
	
	// Bootstrap Select js lang
	var bootstrapSelectJsLang = gulp
		.src("./node_modules/bootstrap-select/dist/js/i18n/defaults-fr_FR.min.js")
		.pipe(gulp.dest(paths.vendorDir + "/bootstrap-select/js"));
	
	// Bootstrap Select css
	var bootstrapSelectCss = gulp
		.src("./node_modules/bootstrap-select/dist/css/bootstrap-select.min.css")
		.pipe(gulp.dest(paths.vendorDir + "/bootstrap-select/css"));

	// Font Awesome
	var fontAwesome = gulp
		.src("./node_modules/@fortawesome/**/*")
		.pipe(gulp.dest(paths.vendorDir));
	  
	// jQuery Easing
	var jqueryEasing = gulp
		.src("./node_modules/jquery.easing/*.js")
		.pipe(gulp.dest(paths.vendorDir + "/jquery-easing"));
	  
	// jQuery
	var jquery = gulp
		.src([
			"./node_modules/jquery/dist/*",
			"!./node_modules/jquery/dist/core.js"
		])
		.pipe(gulp.dest(paths.vendorDir + "/jquery"));
	  
	return merge(
			bootstrapJS, bootstrapSCSS,
			bootstrapSelectJs, bootstrapSelectJsLang, bootstrapSelectCss,
			fontAwesome, jquery, jqueryEasing
	);
}

// CSS task
function css() {
	return gulp
		.src(paths.resourcesDir + "/scss/**/*.scss")
		.pipe(plumber())
		// SASS
		.pipe(sass({
		  outputStyle: "expanded",
		  includePaths: "node_modules",
		}))
		.on("error", sass.logError)
		/*.pipe(autoprefixer({				// Deprecie ?
		  browsers: ["last 2 versions"],
		  cascade: false
		}))*/
		.pipe(header(banner, {
			pkg: pkg
		}))
		.pipe(gulp.dest(paths.resourcesDir + "/css"))
		.pipe(rename({
		  suffix: ".min"
		}))
		// Minification
		.pipe(cleanCSS())
		.pipe(gulp.dest(paths.resourcesDir + "/css"))
		.pipe(browsersync.stream());
}

// JS task
function js() {
	return gulp
	    .src([
	    	paths.resourcesDir + "/js/*.js",
	      "!" + paths.resourcesDir + "/js/*.min.js",
	    ])
	    // Minification
	    .pipe(uglify())
	    .pipe(header(banner, {
	      pkg: pkg
	    }))
	    .pipe(rename({
	      suffix: ".min"
	    }))
	    .pipe(gulp.dest(paths.resourcesDir + "/js"))
		.pipe(browsersync.stream());
}

// Define complex tasks
const vendor = gulp.series(clean, modules);
const build = gulp.series(vendor, gulp.parallel(css, js));

// Export tasks
exports.css = css;
exports.js = js;
exports.clean = clean;
exports.build = build;
exports.default = build;