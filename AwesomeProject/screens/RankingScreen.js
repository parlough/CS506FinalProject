import React from 'react';
import { ScrollView, StyleSheet, Text } from 'react-native';
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
    if(this.state.currentCandidateIndex < this.state.candidates.length - 1){
      var newVal = this.state.currentCandidateIndex + 1
      this.setState( {
        currentCandidateIndex: this.state.currentCandidateIndex + 1,
        currentCandidate: this.state.candidates[newVal]
      })
    }
  }

  render() {
    return (
      <ScrollView style={styles.container}>
      <Text>Candidate: {this.state.currentCandidate}</Text>

      <Button style={{marginTop: 20}}
            full
            rounded
            success
            onPress={()=> this.rank(5)}
            >
        <Text>Great</Text>
      </Button>

      <Button style={{marginTop: 20}}
            full
            rounded
            success
            onPress={()=> this.rank(4)}
            >
        <Text>Good</Text>
      </Button>

      <Button style={{marginTop: 20}}
            full
            rounded
            success
            onPress={()=> this.rank(3)}
            >
        <Text>Average</Text>
      </Button>

      <Button style={{marginTop: 20}}
            full
            rounded
            success
            onPress={()=> this.rank(2)}
            >
        <Text>Lacking</Text>
      </Button>

      <Button style={{marginTop: 20}}
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
});
