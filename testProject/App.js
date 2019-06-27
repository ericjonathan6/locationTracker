/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, {Component} from 'react';
import {Platform, StyleSheet, Text, View, Button} from 'react-native';
import ToastExample from './modules/ToastExample';
import LocationTracker from './modules/LocationTracker';

export default class App extends Component {
  constructor(props) {
    super(props);

    this.state = {
      latitude: null,
      longitude: null
    };

    this.fetchLocation = this.fetchLocation.bind(this);
  }
  
  componentDidMount() {
    ToastExample.show('Awesome', ToastExample.SHORT);

    LocationTracker.read((msg) => {
      console.log('wololo' + msg);
    })

  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>Device current Position:</Text>
        <Text style={styles.instructions}>Latitude: {this.state.latitude}</Text>
        <Text style={styles.instructions}>Longitude: {this.state.longitude}</Text>
        <Text>{"\n"}</Text>
        <Button
          title="Update Position"
          onPress={this.fetchLocation}
        />
              
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});
