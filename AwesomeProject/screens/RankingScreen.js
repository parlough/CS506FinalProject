import React from 'react';
import { ScrollView, StyleSheet, Text, Image } from 'react-native';
import { Button } from 'native-base';
import * as firebase from "firebase";

export default class RankingScreen extends React.Component {
  static navigationOptions = {
    title: 'Ranking',
  };

  constructor(props){
    super(props)
 
    this.state = ({
      currentCandidate: 'Cory Booker',
      currentCandidateIndex: 0,
      candidates: ["Cory Booker", "Donald Trump", "Ted Cruz", "Kamala Harris", "Barack Obama"]
    })
  }

  rank = (rank) => {
    //Store response into firebase    
    var UID = firebase.auth().currentUser.uid;
    firebase.database().ref('Users/' + UID + '/Responses/' + this.state.currentCandidate).set({
      candidateName: this.state.currentCandidate,
      ranking: rank
    })

    //Increment to next candidate
    var newVal = (this.state.currentCandidateIndex + 1)%5
    this.setState( {
      currentCandidateIndex: (this.state.currentCandidateIndex + 1)%5,
      currentCandidate: this.state.candidates[newVal]
    })
  }

  render() {
    return (
      <ScrollView style={styles.container}>
      <Text style={styles.text}>Candidate: {this.state.currentCandidate}</Text>
      <Text style={styles.textSmall}>Party:</Text>
      <Image
          style={styles.image}
          source={uri='https://122g2g321ipu7384u15dtr81-wpengine.netdna-ssl.com/wp-content/uploads/2016/03/THUMB_Cory-Booker.jpg'}
        />
      <Button style={{marginTop: 20, marginLeft: 25, marginRight: 25}}
            full
            rounded
            success
            onPress={()=> this.rank(5)}
            >
        <Text>Great</Text>
      </Button>

      <Button style={{marginTop: 20, marginLeft: 25, marginRight: 25}}
            full
            rounded
            success
            onPress={()=> this.rank(4)}
            >
        <Text>Good</Text>
      </Button>

      <Button style={{marginTop: 20, marginLeft: 25, marginRight: 25}}
            full
            rounded
            success
            onPress={()=> this.rank(3)}
            >
        <Text>Average</Text>
      </Button>

      <Button style={{marginTop: 20, marginLeft: 25, marginRight: 25}}
            full
            rounded
            success
            onPress={()=> this.rank(2)}
            >
        <Text>Lacking</Text>
      </Button>

      <Button style={{marginTop: 20, marginLeft: 25, marginRight: 25}}
            full
            rounded
            success
            onPress={()=> this.rank(1)}
            >
        <Text>Terrible</Text>
      </Button>
      </ScrollView>

      
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    paddingTop: 15,
    backgroundColor: '#fff',
  },

  image: {
    width: 150,
    height: 150,
    justifyContent: 'center'
  },
  
  text: {
    textAlign: 'center',
    color: 'black',
    fontWeight: 'bold',
    fontSize: 24
  },

  textSmall: {
    textAlign: 'center',
    paddingTop: 15,
    color: 'black',
    fontWeight: 'bold',
    fontSize: 16
  },
});