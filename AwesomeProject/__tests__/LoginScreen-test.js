// __tests/LoginScreen-test.js
import 'react-native';
import React from 'react';
import App from '../App';
import renderer from 'react-test-renderer';
import NavigationTestUtils from 'react-navigation/NavigationTestUtils';

import LoginScreen from '../screens/LoginScreen'

var rewire = require('rewire');
var lscreen = rewire('../screens/LoginScreen.js');

const loginUser = lscreen.__get__('loginUser');

test('renders correctly', () => {
	const tree = renderer.create(<LoginScreen />).toJSON();
	expect(tree).toMatchSnapshot();
});
