import React, {Component} from 'react';
import {
  Text,
  ActivityIndicator,
  View,
  StyleSheet,
  FlatList,
} from 'react-native';

import * as firebase from "firebase";
import _ from 'lodash';


export default class ReviewScreen extends React.Component {
  static navigationOptions = {
    title: "Review",
  };

  constructor(props){
    super(props);

    this.state = {
      loading: false,
      friends: []
    }
  }

  updateScreen(){
    console.log("Updating Screen");
    var that = this;
    const currentUser = firebase.auth().currentUser.uid;
    const pairs = []
    firebase.database().ref(`Users/${currentUser}/Responses`).once('value').then(function(snapshot) {
      snapshot.forEach((child) => {
        var pair = {ranking: child.child('ranking').val(), candidate: child.child('candidateName').val()}
        pairs.push(pair);
      });
      pairs.sort(function (a, b) {
        return b.ranking - a.ranking;
      });
      that.setState({friends: pairs})
    });
  }
  

  componentDidMount() {
    this.subs = [
      this.props.navigation.addListener('willFocus', () => this.updateScreen()),
      this.props.navigation.addListener('willBlur', () => console.log('will blur')),
      this.props.navigation.addListener('didFocus', () => console.log('did focus')),
      this.props.navigation.addListener('didBlur', () => console.log('did blur')),
    ];

    this.updateScreen();
  }

  renderItem({item}) {
    return (
        <Text style={styles.item}>{item.candidate}</Text>
      )
  }

  render() {
    if (this.state.loading) {
      return (
        <View style={{alignItems: 'center', justifyContent: 'center', flex: 1}}>
          <ActivityIndicator size="large" color="dodgerblue" />
        </View>
      )
    }

    return (
      <View style={styles.container}>
      <Text style={styles.text}>Reccomended Candidates</Text>
        <FlatList
          data={this.state.friends}
          renderItem={this.renderItem}
          //keyExtractor={item => item.key}
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

