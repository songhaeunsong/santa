type Coordinate = {
  latitude: number;
  longitude: number;
};

export const calculateCenter = (coordinates: Coordinate[]): Coordinate => {
  if (coordinates.length === 0) {
    return { latitude: 37.566826, longitude: 126.9786567 };
  }

  const total = coordinates.length;
  const center = coordinates.reduce(
    (acc, { latitude, longitude }) => {
      acc.lat += latitude / total;
      acc.lng += longitude / total;
      return acc;
    },
    { lat: 0, lng: 0 }
  );

  return {
    latitude: center.lat,
    longitude: center.lng
  };
};

export const calculateZoom = (coordinates: Coordinate[]): number => {
  if (coordinates.length === 0) {
    return 8;
  }

  const lats = coordinates.map(coord => coord.latitude);
  const lngs = coordinates.map(coord => coord.longitude);

  const latRange = Math.max(...lats) - Math.min(...lats);
  const lngRange = Math.max(...lngs) - Math.min(...lngs);

  const maxRange = Math.max(latRange, lngRange);

  if (maxRange < 0.01) {
    return 5;
  }
  if (maxRange < 0.1) {
    return 7;
  }
  if (maxRange < 1) {
    return 10;
  }
  return 8;
};
