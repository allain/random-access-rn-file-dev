import path from 'path'
import randomAccess from 'random-access-storage'

import { NativeModules } from 'react-native';

const { RNRandomAccessRnFile } = NativeModules;

export const cachePath = RNRandomAccessRnFile.cachePath
export const documentPath = RNRandomAccessRnFile.documentPath
export const tempPath = RNRandomAccessRnFile.tempPath

export default function fileAccessor(filePath) {
    return randomAccess({
        open(req) {
            RNRandomAccessRnFile.ensureFileExists(filePath).then(() => {
                console.log('ENSURE EXISTS CALLED BACK')
                req.callback(null)
            }, req.callback)
        },
        read(req) {
            console.log('Calling out to read')
            RNRandomAccessRnFile.read(filePath, req.size, req.offset).then(
                data => {
                    console.log('read data', data)
                    const buffer = bufferFrom(data, 'utf8')
                    if (buffer.length !== req.size) {
                        req.callback(new Error('Range not satisfiable'))
                    } else {
                        req.callback(null, buffer)
                    }
                },
                req.callback
            )
        },
        write(req) {
            const data = new String(req.data, 'utf8')
            RNRandomAccessRnFile.write(filePath, data, req.offset, 'utf8').then(
                () => req.callback(null),
                req.callback
            )
        }
    })
}
