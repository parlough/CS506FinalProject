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

export default class ReviewScreen extends React.Component {
  static navigationOptions = {
    title: "Review",
  };


  render() {
    return (
      <View style={styles.container}>
      <Text>Reccomended Candidates</Text>
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

      <Text>Top Issues</Text>
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
  },
})

