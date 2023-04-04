export interface IDummyData {}

export interface IMetroStationCorrespondence {
  station: IMetroStation,
  correspondence: string[]
}

export interface IMetroStation {
  name: string,
  coords?: ICoordinates
}

export interface ICoordinates {
  lon: number,
  lat: number
}
