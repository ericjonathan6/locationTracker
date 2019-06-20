/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, {Component} from 'react';
import {Platform, StyleSheet, Text, View, Button} from 'react-native';
import BackgroundJob from 'react-native-background-job';
import AsyncStorage from '@react-native-community/async-storage';

const backgroundJob = {
 jobKey: "myJob",
 job: () => {
    console.log("Running in background")
    navigator.geolocation.getCurrentPosition(
      async(position) => {
        console.log(
          position.coords.latitude,
          position.coords.longitude,
        );
        await AsyncStorage.setItem('@MyApp:position', JSON.stringify(position));
      },
      async(error) => await AsyncStorage.setItem('@MyApp:error', JSON.stringify(error.message)),
      { enableHighAccuracy: true, timeout: 20000, maximumAge: 1000 },
    );  
  }
};

BackgroundJob.register(backgroundJob);

type Props = {};
export default class App extends Component<Props> {
  constructor(props) {
    super(props);

    this.state = {
      latitude: null,
      longitude: null
    };

    this.fetchLocation = this.fetchLocation.bind(this);
  }
  
  componentDidMount() {
    BackgroundJob.schedule({
      jobKey: "myJob",
      period: 4000,
      exact: true,
      allowWhileIdle: true,
      timeout: 3000,
      allowExecutionInForeground: true,
    });
    this.interval = setInterval(() => this.fetchLocation(), 2000);
  }

  componentWillUnmount() {
    clearInterval(this.interval);
  }

  async fetchLocation() {
    const position = await AsyncStorage.getItem('@MyApp:position');
    if (position) {
      const result = JSON.parse(position);
      console.log("FETCHING");
      this.setState({
        latitude: result.coords.latitude,
        longitude:  result.coords.longitude,
      });
    }
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
