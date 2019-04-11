import React from 'react';
import { Text, StyleSheet, View, TextInput, ScrollView, TouchableOpacity } from 'react-native';
import { Container, Content, Header, Form, Input, Item, Button, Label } from "native-base"
import * as firebase from "firebase";


export default class SignUpScreen extends React.Component {
    static navigationOptions = {
        title: 'Sign Up Page',
    };

    constructor(props) {
        super(props)

        this.state = ({
            email: '',
            password: '',
            name: ''
        })
    }

    signUpUser = (email, password, name, context) => {
        try {
            if (this.state.password.length < 6) {
                alert("Please enter at least 6 characters")
                return;
            }

            firebase.auth().createUserWithEmailAndPassword(email, password).then(function () {
                var UID = firebase.auth().currentUser.uid;
                var Responses = [];
                firebase.database().ref('Users/' + UID).set({
                    name: name,
                    email: email,
                    Responses: Responses
                })

                context.props.navigation.navigate('Main')
            })

        } catch (error) {
            alert(error.toString)
        }
    }

    // loginUser = (email, password, context) =>{

    //     try{
    //       firebase.auth().signInWithEmailAndPassword(email, password)
    //       .then(function (user) {
    //         console.log(user)
    //         alert("Success")
    //         context.props.navigation.navigate('Main')

    //       });
    //     }catch(error){
    //       alert(error.toString)
    //     }
    // }

    render() {
        return (
            <Container style={styles.container}>
                <Form>
                <Text style={styles.text}>Vote For You!</Text>
                    <Item floatingLabel>
                        <Label>Name</Label>
                        <Input
                            autoCorrect={false}
                            autoCapitalize="none"
                            onChangeText={(name) => this.setState({ name })}
                        />
                    </Item>
                    <Item floatingLabel>
                        <Label>Email</Label>
                        <Input
                            autoCorrect={false}
                            autoCapitalize="none"
                            onChangeText={(email) => this.setState({ email })}
                        />
                    </Item>

                    <Item floatingLabel>
                        <Label>Password</Label>
                        <Input
                            secureTextEntry={true}
                            autoCorrect={false}
                            autoCapitalize="none"
                            onChangeText={(password) => this.setState({ password })}
                        />
                    </Item>

                    <Button style={{ marginTop: 20 }}
                        full
                        rounded
                        success
                        onPress={() => this.signUpUser(this.state.email, this.state.password, this.state.name, this)}
                    >
                        <Text style={{ color: 'white' }}>Sign Up</Text>
                    </Button>

                    <View style={styles.signupTextCont}>
                        <Text style={styles.signUpText}>Already have an account?</Text>
                        <TouchableOpacity
                            onPress={() => this.props.navigation.navigate('Login')}
                        ><Text style={styles.signupButton} >Login</Text></TouchableOpacity>
                    </View>

                    {/* <Button style={{ marginTop: 20 }}
                        full
                        rounded
                        success
                        //onPress={()=> this.loginUser(this.state.email, this.state.password, this)}
                        onPress={() => this.props.navigation.navigate('Login')}
                    >
                        <Text style={{ color: 'white' }}>Login</Text>
                    </Button> */}


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
    },

    text: {
        textAlign: 'center',
            color: 'black',
            fontFamily: 'AmericanTypewriter',
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

