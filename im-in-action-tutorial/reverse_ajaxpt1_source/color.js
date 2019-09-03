//http://designpepper.com/blog/drips/using-javascripts-tostring-method

function Color(r, g, b){
	this.r = r;
	this.g = g;
	this.b = b;
}

Color.prototype.toString = function() {
	return "rgb( " 
		+ this.r + ", " 
		+ this.g + ", " 
		+ this.b + " )";
};

var red = new Color(255, 0, 0);

// speciall handling gives to objects
// {r: 255, g: 0, b: 0} 
console.log(red);

// implicitly call toString() of object
var message = "The color is " + red;
console.log(message);