<?php

$arr['flickr'] = 'soemone';
$arr['yahoo'] = 2009;
$arr['google'] = 2009;

foreach ($arr as $key => $value) {
	echo "The value of $key is $value";
	echo PHP_EOL;
}

echo json_encode($arr);

echo PHP_EOL;

$kyary[2] = 'ninja';
$kyary[0] = 2008;
$kyary[1] = 2000;

foreach ($kyary as $key => $value) {
	echo "The value of $key is $value";
	echo PHP_EOL;
}