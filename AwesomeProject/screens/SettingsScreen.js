import React from 'react';
import { Text, StyleSheet, View, TextInput, ScrollView, TouchableOpacity} from 'react-native';
import {Container, Content, Header, Form, Input, Item, Button, Label} from "native-base"
import * as firebase from "firebase";


export default class SettingsScreen extends React.Component {
  static navigationOptions = {
    title: 'Settings',
  };
  
  SignOut = (context) => {
    firebase.auth().signOut().then(function() {
      // Sign-out successful.
      context.props.navigation.navigate('Login')
    }).catch(function(error) {
      // An error happened.
      alert(error)
    });
  }


  render() {
    return (
      <Container style={styles.container }>
        <Form>
           <Button style={{marginTop: 20}}
            full
            rounded
            success
            onPress={()=> this.SignOut(this)}
            >
            <Text style={{color: 'white'}}>Sign Out</Text>
            </Button>
        </Form>
      </Container>
    
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#ffff',
    justifyContent: 'center',
    padding: 10
  }

})
