declare module 'react-native-bixolon-printer' {
  type DeviceAvailableResult = {
    isSuccess: boolean
    error: string
    isDeviceAvailable: boolean
  }

  type DeviceAvailableCallback = (result: DeviceAvailableResult) => void
  function isDeviceAvailable(callback: DeviceAvailableCallback): void

  type OperationResult = {
    isSuccess: boolean
    error: string
  }

  type Callback = (result: OperationResult) => void

  function autoConnect(callback: Callback): void
  function disconnect(callback: Callback): void
  function printPdf(pdfPath: string, brightness: number, callback: Callback): void
}
