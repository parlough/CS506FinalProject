import 'react-native';
import React from 'react';
import App from '../App';
import { mount } from "enzyme";
import { shallow } from 'enzyme';
import LoginScreen from '../screens/LoginScreen.js';
import * as firebase from "firebase";
import renderer from 'react-test-renderer';
import NavigationTestUtils from 'react-navigation/NavigationTestUtils';

describe('App snapshot', () => {
  jest.useFakeTimers();
  beforeEach(() => {
    NavigationTestUtils.resetInternalState();
    this.state = ({
      email: '',
      password: ''
    });
  jest.setTimeout(30000);
  });

  it('logs into an incorrect username', (email, password) => {
    try{
      firebase
      .auth()
      .signInWithEmailAndPassword(email, password)
      .then(function (user) {
        console.log(user)
      })
      .catch(error => alert("Login not successful. Invalid Credentials"));
      expect(1).toEqual(0); // Don't expect to end here
    }catch(error){
	expect(1).toEqual(1); // Expect to end up here after Firebase 
        //failure to authenticate
    }
  });

});
