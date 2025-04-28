
declare module 'react-native-slider' {
    import { Component } from 'react';
    
    interface SliderProps {
      value: number;
      onValueChange: (value: number) => void;
      minimumValue?: number;
      maximumValue?: number;
      step?: number;
      thumbTintColor?: string;
      minimumTrackTintColor?: string;
      maximumTrackTintColor?: string;
      style?: object;
    }
  
    export default class Slider extends Component<SliderProps> {}
  }
  