import React, {Component} from 'react';
import {
  AppRegistry,
  ListView,
  Text,
  ActivityIndicator,
  View,
  StyleSheet,
  FlatList,
} from 'react-native';

import * as firebase from "firebase";
import { BorderlessButton } from 'react-native-gesture-handler';

export default class ReviewScreen extends React.Component {
  static navigationOptions = {
    title: "Review",
  };


  render() {
    return (
      <View style={styles.container}>
      <Text style={styles.text}>Reccomended Candidates</Text>
        <FlatList
          data={[
            {key: 'Cory Booker'},
            {key: 'Donald Trump'},
            {key: 'Ted Cruz'},
            {key: 'Kamala Harris'},
            {key: 'Barack Obama'},
          ]}
          renderItem={({item}) => <Text style={styles.item}>{item.key}</Text>}
        />

      <Text style={styles.text}>Top Issues</Text>
        <FlatList
          data={[
            {key: 'Gun Control'},
            {key: 'Abortion'},
            {key: 'Foreign Policy'},
            {key: 'Economy'},
          ]}
          renderItem={({item}) => <Text style={styles.item}>{item.key}</Text>}
        />
      </View>
    );
  }


  
}

const styles = StyleSheet.create({
  container: {
   flex: 1,
   paddingTop: 22
  },
  item: {
    padding: 10,
    fontSize: 18,
    height: 44,
    textAlign: 'center'
  },
  text: {
    textAlign: 'center',
    color: 'black',
    fontWeight: 'bold',
    fontSize: 24
  },
})

