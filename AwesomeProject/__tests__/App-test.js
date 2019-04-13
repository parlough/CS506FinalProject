import 'react-native';
import React from 'react';
import App from '../App';
import { mount } from "enzyme";
import { shallow } from 'enzyme';
import LoginScreen from '../screens/LoginScreen.js';
import RankingScreen from '../screens/RankingScreen.js';
import ReviewScreen from '../screens/ReviewScreen.js';
import SettingsScreen from '../screens/SettingsScreen.js';
import SignUpScreen from '../screens/SignUpScreen.js';
import renderer from 'react-test-renderer';
import NavigationTestUtils from 'react-navigation/NavigationTestUtils';

describe('App snapshot', () => {
  jest.useFakeTimers();
  beforeEach(() => {
    NavigationTestUtils.resetInternalState();
  });

  it('renders the loading screen', async () => {
    const tree = renderer.create(<App />).toJSON();
    expect(tree).toMatchSnapshot();
  });

  it('renders the root without loading screen', async () => {
    const tree = renderer.create(<App skipLoadingScreen />).toJSON();
    expect(tree).toMatchSnapshot();
  });

  it('renders the login screen correctly', async () => {
    const tree = renderer.create(<LoginScreen />).toJSON();
    expect(tree).toMatchSnapshot();
  });	

  it('renders the ranking screen correctly', async () => {
    const tree = renderer.create(<RankingScreen />).toJSON();
    expect(tree).toMatchSnapshot();
  });	

  it('renders the review screen correctly', async () => {
    const tree = renderer.create(<ReviewScreen />).toJSON();
    expect(tree).toMatchSnapshot();
  });	

  it('renders the settings screen correctly', async () => {
    const tree = renderer.create(<SettingsScreen />).toJSON();
    expect(tree).toMatchSnapshot();
  });

  it('renders the signup screen correctly', async () => {
    const tree = renderer.create(<SignUpScreen />).toJSON();
    expect(tree).toMatchSnapshot();
  });	

});
