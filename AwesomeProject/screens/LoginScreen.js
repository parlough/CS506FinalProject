import React from 'react';
import { Text, StyleSheet, View, TextInput, ScrollView, TouchableOpacity } from 'react-native';
import {Container, Content, Header, Form, Input, Item, Button, Label} from "native-base"
import { AppRegistry, Image } from 'react-native';
import * as firebase from "firebase";


export default class LoginScreen extends React.Component {
  static navigationOptions = {
    title: 'Login Page',
  };

  constructor(props){
    super(props)
 
    this.state = ({
      email: '',
      password: ''
    })
  }

loginUser = (email, password, context) =>{

  if (email.length < 1) {
    alert("Please enter a valid password")
    return;
  }

  if (password.length < 1) {
    alert("Please enter a valid password")
    return;
  }

  var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
  if(!email.match(mailformat)){
    alert("Invalid email format")
    return;
  }
  
    try{
      firebase
      .auth()
      .signInWithEmailAndPassword(email, password)
      .then(function (user) {
        console.log(user)
        context.props.navigation.navigate('Main')
        
      })
      .catch(error => alert("Login not successful. Invalid Credentials"));
    }catch(error){
      alert(error.toString)
    }
}

render() {

    return (
      
      <Container style={styles.container }>
        <Form>
          <Text style={styles.text}>Vote For You!</Text>
          <Item floatingLabel>
            <Label>Email</Label>
            <Input
              autoCorrect={false}
              autoCapitalize="none"
              onChangeText={(email) => this.setState({email})}
              />
           </Item>
           
           <Item floatingLabel>
            <Label>Password</Label>
            <Input
              secureTextEntry={true}
              autoCorrect={false}
              autoCapitalize="none"
              onChangeText={(password) => this.setState({password})}
              />
           </Item>

           <Button style={{color: 'black', marginTop: 20}}
            full
            rounded
            success
            onPress={()=> this.loginUser(this.state.email, this.state.password, this)}
            >
            <Text style={{color: 'white'}}>Login</Text>
            </Button>

            <View style = {styles.signupTextCont}>
              <Text style = {styles.signUpText}>Don't have an account yet? </Text>
              <TouchableOpacity
              onPress={()=> this.props.navigation.navigate('SignUp')}
              ><Text style = {styles.signupButton} >Signup</Text></TouchableOpacity>
            </View>
            
            {/* <Button style={{marginTop: 20}}
            full
            rounded
            primary
            //onPress={()=> this.signUpUser(this.state.email, this.state.password)}
            onPress={()=> this.props.navigation.navigate('SignUp')}
            >
            <Text style={{color: 'white'}}>Sign Up</Text>
            </Button> */}
        </Form>
      </Container>
    
    );
  }
    
}
    
const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'white',
    justifyContent: 'center',
    padding: 10
  },

  text: {
    textAlign: 'center',
        color: 'black',
        //fontFamily: 'AmericanTypewriter',
        fontSize: 40
  },

  signupTextCont:{
    flexGrow: 1,
    alignItems: 'flex-end',
    justifyContent: "center",
    paddingVertical: 16,
    flexDirection: 'row'
  },

  signUpText: {
    color:'rgba(128,128,128,0.6)',
    fontSize: 16
  },

  signupButton: {
    color: '#000000',
    fontSize: 16,
    fontWeight: '500'
  }

})

